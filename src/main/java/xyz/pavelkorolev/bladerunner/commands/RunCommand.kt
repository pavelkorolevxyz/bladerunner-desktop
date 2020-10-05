package xyz.pavelkorolev.bladerunner.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file
import xyz.pavelkorolev.bladerunner.entities.NamingStrategy
import xyz.pavelkorolev.bladerunner.services.NamingService
import xyz.pavelkorolev.bladerunner.services.PrintWriterFactory
import xyz.pavelkorolev.bladerunner.services.RunnerListener
import xyz.pavelkorolev.bladerunner.services.RunnerService
import java.io.File

/**
 * Copy all unique files from given input directory to output directory
 */
class RunCommand(
    private val runnerService: RunnerService,
    private val namingService: NamingService
) : CliktCommand(
    name = "run"
) {

    private val directoryIn by option(
        "-din",
        "--directory-in",
        help = "Path to root directory of input"
    )
        .file(
            exists = true,
            fileOkay = false,
            folderOkay = true
        )
        .required()

    private val directoryOut by option(
        "-dout",
        "--directory-out",
        help = "Path to output directory"
    )
        .file(
            fileOkay = false,
            folderOkay = true
        )
        .required()

    private val namingStrategy by option(
        "-ns",
        "--naming-strategy",
        help = "Naming strategy for created files"
    )
        .enum<NamingStrategy>()
        .default(NamingStrategy.DEFAULT)

    private val out by option(
        "-o",
        "--out",
        help = "Path to output file"
    )
        .file(
            fileOkay = true,
            folderOkay = false
        )

    private val isSilent by option(
        "-s",
        "--silent",
        help = "Do not log activity"
    )
        .flag()

    private val isFlatten by option(
        "-f",
        "--flatten",
        help = "Copy all files into out directory without saving directory tree"
    )
        .flag()

    override fun run() {
        val total = runnerService.getTotalFileCount(directoryIn)
        var copied = 0
        var ignored = 0

        fun countString(): String = "Copied: $copied/$total. Ignored: $ignored"

        val writer = PrintWriterFactory.create(out)

        runnerService.processFiles(directoryIn, object : RunnerListener {

            override fun onFileOk(file: File) {
                val directoryOut = when (isFlatten) {
                    true -> directoryOut
                    false -> {
                        val directoryRelative = file.relativeTo(directoryIn).parent
                        File(directoryOut, directoryRelative)
                    }
                }
                val outputFile = getUniqueOutputFile(file, directoryOut)
                file.copyTo(outputFile)
                if (isSilent) return
                copied++
                writer.println("COPY $file as ${outputFile.name}. ${countString()}")
            }

            override fun onFileClone(file: File, original: File) {
                if (isSilent) return
                ignored++
                writer.println("IGNORED $file. ${countString()}")
            }

            override fun onCompleted() {
                writer.flush()
                writer.close()
            }

        })
    }

    /**
     * Returns file named without UUID if it doesn't exist.
     * Otherwise UUID used in file name.
     */
    private fun getUniqueOutputFile(file: File, directoryOut: File): File {
        val fileName = namingService.generateName(file, namingStrategy)
        val outputFile = File(directoryOut, fileName)
        if (!outputFile.exists()) return outputFile

        val anotherFileName = namingService.generateName(file, namingStrategy, addUuid = true)
        return File(directoryOut, anotherFileName)
    }

}
