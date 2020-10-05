package xyz.pavelkorolev.bladerunner.services

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import xyz.pavelkorolev.bladerunner.utils.TestData

internal class HashServiceImplTest {

    private val hashService: HashService = HashServiceImpl()

    @Test
    fun `calculate hash for text file EXPECT md5 string`() {
        val expected = "01d14d5f95150ee92b55d07354e325bc"

        val file = TestData.getTextFile("plain-text.txt")
        val actual = hashService.calculateHash(file)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `calculate hash for same text file with another name EXPECT same md5 string`() {
        val expected = "01d14d5f95150ee92b55d07354e325bc"

        val file = TestData.getTextFile("nested/plain-text.txt")
        val actual = hashService.calculateHash(file)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `calculate hash for image file EXPECT md5 string`() {
        val expected = "406958840ad1665ffcd1be9c29d515b9"

        val file = TestData.getImageFile("Canon_40D.jpg")
        val actual = hashService.calculateHash(file)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `calculate hash for same image file with another name EXPECT same md5 string`() {
        val expected = "406958840ad1665ffcd1be9c29d515b9"

        val file = TestData.getImageFile("Canon_40D_copy.jpg")
        val actual = hashService.calculateHash(file)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

}