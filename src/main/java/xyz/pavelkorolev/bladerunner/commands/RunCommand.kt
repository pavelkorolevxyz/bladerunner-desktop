package xyz.pavelkorolev.bladerunner.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import xyz.pavelkorolev.bladerunner.services.RunnerService
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintWriter

class RunCommand(
    private val finder: RunnerService
) : CliktCommand(
    name = "run"
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
        PrintWriter(outputStream).use { writer ->
            finder.printDuplicates(file, writer)
        }
    }

    private fun createOutputStream(): OutputStream {
        val out = out ?: return System.out
        return FileOutputStream(out)
    }

}
