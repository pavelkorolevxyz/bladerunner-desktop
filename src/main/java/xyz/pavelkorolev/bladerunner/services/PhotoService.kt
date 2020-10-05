package xyz.pavelkorolev.bladerunner.services

import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.metadata.exif.ExifSubIFDDirectory
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Service to work with photo files. Photo files get date from EXIF at first.
 * It uses modified date if not found in EXIF.
 */
interface PhotoService {

    /**
     * Returns file date. Uses EXIF date for photos, last modified date otherwise
     */
    fun getDate(file: File): Date?

}

class PhotoServiceImpl : PhotoService {

    override fun getDate(file: File): Date? = try {
        val metadata = ImageMetadataReader.readMetadata(file)
        val dir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
        dir?.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)
    } catch (e: ImageProcessingException) {
        null
    } catch (e: IOException) {
        null
    }

}