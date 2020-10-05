package xyz.pavelkorolev.bladerunner.services

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.util.*

internal class PrintWriterFactoryTest {

    @Test
    fun `create with no file EXPECT print writer`() {
        val actual = PrintWriterFactory.create(null)
        Assertions.assertThat(actual).isNotNull
    }

    @Test
    fun `create with file EXPECT print writer`() {
        val outDirectory = File("out")
        val outputFile = File(outDirectory, "output.txt")
        val actual = PrintWriterFactory.create(outputFile)
        Assertions.assertThat(actual).isNotNull
    }
}