package xyz.pavelkorolev.bladerunner.utils

import java.io.File

/**
 * Helper to get test data
 */
object TestData {

    val directory = File("test-data")

    private val textDirectory = File(directory, "text")
    private val imagesDirectory = File(directory, "images")

    /**
     * Returns file by name
     */
    fun getFile(name: String): File = File(directory, name)

    /**
     * Returns text file by name
     */
    fun getTextFile(name: String): File = File(textDirectory, name)

    /**
     * Returns image file by name
     */
    fun getImageFile(name: String): File = File(imagesDirectory, name)

}