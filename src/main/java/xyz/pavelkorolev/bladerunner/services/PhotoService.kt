package xyz.pavelkorolev.bladerunner.services

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.file.FileSystemDirectory
import java.io.File
import java.util.*

interface PhotoService {

    fun getDate(file: File): Date?

}

class PhotoServiceImpl {

    fun getDate(file: File): Date? {
        try {
            val metadata = ImageMetadataReader.readMetadata(file)
            val dir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
            val dateOriginal = dir?.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)

            val dirFile = metadata.getFirstDirectoryOfType(FileSystemDirectory::class.java)
            val dateFile = dirFile?.getDate(FileSystemDirectory.TAG_FILE_MODIFIED_DATE)
            if (dateOriginal == null && dateFile == null) {
                println(file)
            }
            if (dateOriginal != null) {
                return dateOriginal
            }
            if (dateFile != null) {
                return dateFile
            }
        } catch (e: Exception) {
            println(file)
            println(e.printStackTrace())
        }
        return null
    }

}