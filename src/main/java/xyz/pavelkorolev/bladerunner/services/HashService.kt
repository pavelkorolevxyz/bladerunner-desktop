package xyz.pavelkorolev.bladerunner.services

import java.io.File
import java.security.MessageDigest

/**
 * Service to calculate hash of files
 */
interface HashService {

    /**
     * Calculates string hash of given [file]
     */
    fun calculateHash(file: File): String

}

class HashServiceImpl : HashService {

    private val messageDigest = MessageDigest.getInstance("MD5")

    override fun calculateHash(file: File): String {
        file.forEachBlock { buffer: ByteArray, bytesRead: Int ->
            messageDigest.update(buffer, 0, bytesRead)
        }

        val digest = messageDigest.digest()
        return digest.joinToString("") { "%02x".format(it) }
    }

}