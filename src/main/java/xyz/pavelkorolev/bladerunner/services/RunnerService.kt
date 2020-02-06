package xyz.pavelkorolev.bladerunner.services

import java.io.File
import java.io.PrintWriter

interface RunnerService {

    fun printDuplicates(root: File, writer: PrintWriter)

}

class RunnerServiceImpl(
    private val hashService: HashService
) : RunnerService {

    override fun printDuplicates(root: File, writer: PrintWriter) {
        val map = mutableMapOf<String, File>()

        for (file in root.walkTopDown()) {
            if (file.isDirectory) continue

            val hash = hashService.calculateHash(file)
            if (hash in map) {
                writer.println(file)
                continue
            }

            map[hash] = file
        }
        writer.flush()
    }

}
