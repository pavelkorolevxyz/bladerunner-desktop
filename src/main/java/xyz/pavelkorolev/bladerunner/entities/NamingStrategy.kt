package xyz.pavelkorolev.bladerunner.entities

/**
 * Naming strategy for files
 */
enum class NamingStrategy {

    /**
     * Use same file name
     */
    DEFAULT,

    /**
     * Use last modified date as file name. Default otherwise
     */
    DATE_MODIFIED,

    /**
     * Use photo taken date from EXIF. Default otherwise.
     */
    PHOTO_TAKEN
}
