package xyz.pavelkorolev.bladerunner.services

import java.io.File

interface RunnerListener {

    fun onFileOk(file: File) = Unit

    fun onFileClone(file: File) = Unit

    fun onCompleted() = Unit

}

interface RunnerService {

    fun processFiles(root: File, listener: RunnerListener)

}

class RunnerServiceImpl(
    private val hashService: HashService
) : RunnerService {

    override fun processFiles(root: File, listener: RunnerListener) {
        val map = mutableMapOf<String, File>()

        for (file in root.walkTopDown()) {
            if (file.isDirectory) continue

            val hash = hashService.calculateHash(file)
            if (hash in map) {
                listener.onFileClone(file)
                continue
            }
            listener.onFileOk(file)
            map[hash] = file
        }

        listener.onCompleted()
    }

}
