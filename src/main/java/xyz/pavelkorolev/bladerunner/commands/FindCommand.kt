package xyz.pavelkorolev.bladerunner.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import xyz.pavelkorolev.bladerunner.services.RunnerListener
import xyz.pavelkorolev.bladerunner.services.RunnerService
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintWriter

/**
 * Command to find and print file clones
 */
class FindCommand(
    private val runningService: RunnerService
) : CliktCommand(
    name = "find"
) {

    private val directoryPath by option(
        "-d",
        "--directory",
        help = "Path to root directory"
    )

    private val out by option(
        "-o",
        "--out",
        help = "Path to output file"
    )

    override fun run() {
        val directoryPath = directoryPath ?: return

        val file = File(directoryPath)
        if (!file.exists()) return
        if (!file.isDirectory) return

        val outputStream = createOutputStream()
        val writer = PrintWriter(outputStream)

        runningService.processFiles(file, object : RunnerListener {

            override fun onFileOk(file: File) {
                writer.println("OK $file")
            }

            override fun onFileClone(file: File, original: File) {
                writer.println("CLONE $file of $original")
            }

            override fun onCompleted() {
                writer.flush()
                writer.close()
            }

        })
    }

    /**
     * Creates output stream based on out option
     */
    private fun createOutputStream(): OutputStream {
        val out = out ?: return System.out
        return FileOutputStream(out)
    }

}
