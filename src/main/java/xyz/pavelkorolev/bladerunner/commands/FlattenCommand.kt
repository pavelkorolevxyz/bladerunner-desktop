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
 * Flattens all unique files into one directory
 */
class FlattenCommand(
    private val runningService: RunnerService,
    private val namingService: NamingService
) : CliktCommand(
    name = "flatten"
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
        .default(NamingStrategy.MODIFIED_DATE)

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

    override fun run() {
        val total = runningService.getTotalFileCount(directoryIn)
        var copied = 0
        var ignored = 0

        fun countString(): String = "Copied: $copied/$total. Ignored: $ignored"

        val writer = PrintWriterFactory.create(out)

        runningService.processFiles(directoryIn, object : RunnerListener {

            override fun onFileOk(file: File) {
                val fileName = namingService.generateName(file, namingStrategy)
                val outputFile = File(directoryOut, fileName)
                file.copyTo(outputFile)
                if (isSilent) return
                copied++
                writer.println("COPY $file as $fileName. ${countString()}")
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

}
