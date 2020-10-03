package xyz.pavelkorolev.bladerunner.services

import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintWriter

object PrintWriterFactory {

    /**
     * Creates [PrintWriter] based on [outFile] or [System.out] if [outFile] not provided
     */
    fun create(outFile: File? = null): PrintWriter {
        val outputStream = createOutputStream(outFile)
        return PrintWriter(outputStream)
    }

    /**
     * Creates [OutputStream] based on [outFile] or [System.out] if [outFile] not provided
     */
    private fun createOutputStream(outFile: File?): OutputStream {
        val out = outFile ?: return System.out
        return FileOutputStream(out)
    }

}