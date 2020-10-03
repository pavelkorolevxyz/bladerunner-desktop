package xyz.pavelkorolev.bladerunner.entities

/**
 * Naming strategy for files
 */
enum class NamingStrategy {

    /**
     * Use last modified date as file name. Default.
     */
    MODIFIED_DATE,

    /**
     * Use photo taken date from EXIF. Default otherwise.
     */
    PHOTO_TAKEN
}
