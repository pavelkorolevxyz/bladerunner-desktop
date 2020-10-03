package xyz.pavelkorolev.bladerunner.services

import xyz.pavelkorolev.bladerunner.entities.NamingStrategy
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
    fun generateName(
        file: File,
        strategy: NamingStrategy
    ): String

}

class NamingServiceImpl(
    private val photoService: PhotoService,
    private val fileService: FileService
) : NamingService {

    override fun generateName(
        file: File,
        strategy: NamingStrategy
    ): String {
        val date = when (strategy) {
            NamingStrategy.MODIFIED_DATE -> null // Will be used by default anyway
            NamingStrategy.PHOTO_TAKEN -> photoService.getDate(file)
        } ?: fileService.getModifyDate(file)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
        val dateString = dateFormat.format(date)

        val uuid = UUID.randomUUID().toString()

        val extension = file.extension
        return "${dateString}_${uuid}.$extension".toLowerCase()
    }

}