package xyz.pavelkorolev.bladerunner.services

import java.io.File
import java.nio.file.Files

interface FileService {

    fun getContentType(file: File): String?

}

class FileServiceImpl : FileService {

    override fun getContentType(file: File): String? {
        return Files.probeContentType(file.toPath())
    }

}