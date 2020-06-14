package xyz.pavelkorolev.bladerunner.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import xyz.pavelkorolev.bladerunner.services.RunnerListener
import xyz.pavelkorolev.bladerunner.services.RunnerService
import xyz.pavelkorolev.bladerunner.utils.logIf
import java.io.File

/**
 * Deletes all clone files
 */
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

    private val isSilent by option(
        "-s",
        "--silent",
        help = "Do not log activity"
    ).flag()

    override fun run() {
        val directoryPath = directoryPath ?: return

        val file = File(directoryPath)
        if (!file.exists()) return
        if (!file.isDirectory) return

        runningService.processFiles(file, object : RunnerListener {

            override fun onFileClone(file: File, original: File) {
                val isDeleted = file.delete()
                val logCondition = isDeleted && !isSilent
                logIf(logCondition, "DELETE $file")
            }

        })
    }

}
