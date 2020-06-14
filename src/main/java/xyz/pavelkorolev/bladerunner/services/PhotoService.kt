package xyz.pavelkorolev.bladerunner.services

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.file.FileSystemDirectory
import java.io.File
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

    override fun getDate(file: File): Date? {
        try {
            val metadata = ImageMetadataReader.readMetadata(file)
            val dir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
            val dateOriginal = dir?.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)

            val dirFile = metadata.getFirstDirectoryOfType(FileSystemDirectory::class.java)
            val dateFile = dirFile?.getDate(FileSystemDirectory.TAG_FILE_MODIFIED_DATE)
            if (dateOriginal != null) {
                return dateOriginal
            }
            if (dateFile != null) {
                return dateFile
            }
        } catch (e: Exception) {
            val millis = file.lastModified()
            return Date(millis)
        }
        return null
    }

}