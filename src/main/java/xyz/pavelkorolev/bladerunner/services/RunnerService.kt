package xyz.pavelkorolev.bladerunner.services

import java.io.File

/**
 * Callback events of runner service processing
 */
interface RunnerListener {

    /**
     * Called when file found for the first time
     */
    fun onFileOk(file: File) = Unit

    /**
     * Called when there's already clone of this file
     */
    fun onFileClone(file: File, original: File) = Unit

    /**
     * Called when run completed
     */
    fun onCompleted() = Unit

}

/**
 * Service to iterate on files and find clones
 */
interface RunnerService {

    /**
     * Iterates recursively over all files in [root] directory
     */
    fun processFiles(root: File, listener: RunnerListener)

    /**
     * Iterates recursively over all files and calculates file count
     */
    fun getTotalFileCount(root: File): Int

}

class RunnerServiceImpl(
    private val hashService: HashService
) : RunnerService {

    override fun processFiles(root: File, listener: RunnerListener) {
        val map = mutableMapOf<String, File>()

        for (file in root.walkTopDown()) {
            if (file.isDirectory) continue

            val hash = hashService.calculateHash(file)
            val original = map[hash]
            if (original != null) {
                listener.onFileClone(file, original)
                continue
            }
            listener.onFileOk(file)
            map[hash] = file
        }

        listener.onCompleted()
    }

    override fun getTotalFileCount(root: File): Int {
        var count = 0
        for (file in root.walkTopDown()) {
            if (file.isDirectory) continue

            count++
        }
        return count
    }

}
