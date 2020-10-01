package xyz.pavelkorolev.bladerunner.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import xyz.pavelkorolev.bladerunner.services.NamingService
import xyz.pavelkorolev.bladerunner.services.RunnerListener
import xyz.pavelkorolev.bladerunner.services.RunnerService
import xyz.pavelkorolev.bladerunner.utils.logIf
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
    ).file()

    private val isSilent by option(
        "-s",
        "--silent",
        help = "Do not log activity"
    ).flag()

    override fun run() {
        val total = runningService.getTotalFileCount(directoryIn)
        var copied = 0
        var ignored = 0

        fun countString(): String = "Copied: $copied/$total. Ignored: $ignored"

        runningService.processFiles(directoryIn, object : RunnerListener {

            override fun onFileOk(file: File) {
                val fileName = namingService.generateName(file)
                val outputFile = File(directoryOut, fileName)
                file.copyTo(outputFile)
                copied++
                logIf(!isSilent, "COPY $file as $fileName. ${countString()}")
            }

            override fun onFileClone(file: File, original: File) {
                ignored++
                logIf(!isSilent, "IGNORED $file. ${countString()}")
            }

        })
    }

}
