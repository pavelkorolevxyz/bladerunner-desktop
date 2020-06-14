package xyz.pavelkorolev.bladerunner.services

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Service to create new file names based on properties
 */
interface NamingService {

    /**
     * Generates new name for given [file]
     */
    fun generateName(file: File): String

}

class NamingServiceImpl(
    private val photoService: PhotoService,
    private val fileService: FileService
) : NamingService {

    override fun generateName(file: File): String {
        val date = photoService.getDate(file)
            ?: fileService.getModifyDate(file)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
        val dateString = dateFormat.format(date)

        val uuid = UUID.randomUUID().toString()

        val extension = file.extension
        return "${dateString}_${uuid}.$extension".toLowerCase()
    }

}