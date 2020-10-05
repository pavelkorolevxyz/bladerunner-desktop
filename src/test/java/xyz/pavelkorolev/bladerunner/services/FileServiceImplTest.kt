package xyz.pavelkorolev.bladerunner.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xyz.pavelkorolev.bladerunner.utils.TestData

internal class FileServiceImplTest {

    private val fileService: FileService = FileServiceImpl()

    @Test
    fun `get name EXPECT file name without extension`() {
        val expected = "plain-text"

        val file = TestData.getTextFile("plain-text.txt")
        val actual = fileService.getName(file)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get content type for text EXPECT text plain`() {
        val expected = "text/plain"

        val file = TestData.getTextFile("plain-text.txt")
        val actual = fileService.getContentType(file)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get content type for text EXPECT image jpeg`() {
        val expected = "image/jpeg"

        val file = TestData.getImageFile("Canon_40D.jpg")
        val actual = fileService.getContentType(file)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get modify date for text EXPECT file modified date`() {
        val file = TestData.getTextFile("plain-text.txt")

        val expected = file.lastModified()
        val actual = fileService.getModifyDate(file).time

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get modify date for image EXPECT file modified date`() {
        val file = TestData.getImageFile("Canon_40D.jpg")

        val expected = file.lastModified()
        val actual = fileService.getModifyDate(file).time

        assertThat(actual).isEqualTo(expected)
    }

}

