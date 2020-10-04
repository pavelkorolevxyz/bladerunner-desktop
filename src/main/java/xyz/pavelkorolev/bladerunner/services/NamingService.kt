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
     * Generates output name for given [file] using [strategy].
     * [addUuid] parameter can be used to add unique suffix.
     */
    fun generateName(
        file: File,
        strategy: NamingStrategy,
        addUuid: Boolean = false
    ): String

}

class NamingServiceImpl(
    private val photoService: PhotoService,
    private val fileService: FileService
) : NamingService {

    companion object {
        private val dateFormatter = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
    }

    override fun generateName(
        file: File,
        strategy: NamingStrategy,
        addUuid: Boolean
    ): String {
        val name = when (strategy) {
            NamingStrategy.DEFAULT -> null // Will be used by default anyway
            NamingStrategy.DATE_MODIFIED -> {
                val modifiedDate = fileService.getModifyDate(file)
                modifiedDate.formatted()
            }
            NamingStrategy.PHOTO_TAKEN -> {
                val photoTakenDate = photoService.getDate(file)
                photoTakenDate?.formatted()
            }
        } ?: fileService.getName(file)

        val builder = StringBuilder()
            .append(name)

        if (addUuid) {
            val uuid = UUID.randomUUID().toString()
            builder
                .append("_")
                .append(uuid)
        }

        val extension = file.extension
        if (extension.isNotEmpty()) {
            builder
                .append(".")
                .append(extension.toLowerCase())
        }

        return builder.toString()
    }

    /**
     * Formats date with local formatter
     */
    private fun Date.formatted() = dateFormatter.format(this)

}