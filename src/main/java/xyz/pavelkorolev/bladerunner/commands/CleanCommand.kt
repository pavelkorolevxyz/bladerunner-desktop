package xyz.pavelkorolev.bladerunner.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import xyz.pavelkorolev.bladerunner.services.PrintWriterFactory
import xyz.pavelkorolev.bladerunner.services.RunnerListener
import xyz.pavelkorolev.bladerunner.services.RunnerService
import java.io.File

/**
 * Deletes all clone files
 */
class CleanCommand(
    private val runningService: RunnerService
) : CliktCommand(
    name = "clean"
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
    ).flag()

    override fun run() {
        val writer = PrintWriterFactory.create(out)

        runningService.processFiles(directoryIn, object : RunnerListener {

            override fun onFileClone(file: File, original: File) {
                val isDeleted = file.delete()
                if (isSilent || !isDeleted) return
                writer.println("DELETE $file")
            }

            override fun onCompleted() {
                writer.flush()
                writer.close()
            }

        })
    }

}
