package xyz.pavelkorolev.bladerunner.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import xyz.pavelkorolev.bladerunner.services.RunnerListener
import xyz.pavelkorolev.bladerunner.services.RunnerService
import java.io.File

class CleanCommand(
    private val runningService: RunnerService
) : CliktCommand(
    name = "clean"
) {

    private val directoryPath by option(
        "-d",
        "--directory",
        help = "Path to root directory"
    )

    override fun run() {
        val directoryPath = directoryPath ?: return

        val file = File(directoryPath)
        if (!file.exists()) return
        if (!file.isDirectory) return

        runningService.processFiles(file, object : RunnerListener {

            override fun onFileClone(file: File) {
                file.delete()
            }

        })
    }

}
