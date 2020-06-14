package xyz.pavelkorolev.bladerunner.utils

/**
 * Prints message if condition met
 */
fun logIf(condition: Boolean, message: Any?) {
    if (!condition) return
    println(message)
}