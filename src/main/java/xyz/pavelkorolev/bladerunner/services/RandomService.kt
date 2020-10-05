package xyz.pavelkorolev.bladerunner.services

import java.util.*

/**
 * Service to work with random data
 */
interface RandomService {

    /**
     * Return random UUID string
     */
    fun generateUuidString(): String

}

class RandomServiceImpl : RandomService {

    override fun generateUuidString(): String =
        UUID.randomUUID().toString()

}