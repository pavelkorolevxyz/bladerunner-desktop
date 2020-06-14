package xyz.pavelkorolev.bladerunner.services

import java.io.File
import java.nio.file.Files
import java.util.*

/**
 * Service to get file info
 */
interface FileService {

    /**
     * Returns file content type if possible
     */
    fun getContentType(file: File): String?

    /**
     * Returns file last modified date
     */
    fun getModifyDate(file: File): Date

}

class FileServiceImpl : FileService {

    override fun getContentType(file: File): String? {
        return Files.probeContentType(file.toPath())
    }

    override fun getModifyDate(file: File): Date {
        return Date(file.lastModified())
    }

}