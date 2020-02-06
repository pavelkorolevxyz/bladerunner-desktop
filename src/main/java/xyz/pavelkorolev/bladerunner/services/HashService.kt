package xyz.pavelkorolev.bladerunner.services

import java.io.File
import java.security.MessageDigest

interface HashService {

    fun calculateHash(file: File): String

}

class HashServiceImpl : HashService {

    private val messageDigest = MessageDigest.getInstance("SHA-1")

    override fun calculateHash(file: File): String {
        val bytes = file.readBytes()
        val digest = messageDigest.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

}