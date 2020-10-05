package xyz.pavelkorolev.bladerunner.services

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import xyz.pavelkorolev.bladerunner.utils.TestData
import java.util.*

internal class PhotoServiceImplTest {

    private val photoService: PhotoService = PhotoServiceImpl()

    @Test
    fun `get date for canon exif EXPECT taken date`() {
        val expected = Date(1212162961000)

        val file = TestData.getImageFile("Canon_40D.jpg")
        val actual = photoService.getDate(file)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get date for canon copy exif EXPECT taken date`() {
        val expected = Date(1212162961000)

        val file = TestData.getImageFile("Canon_40D_copy.jpg")
        val actual = photoService.getDate(file)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get date for kodak exif EXPECT taken date`() {
        val expected = Date(1123926443000)

        val file = TestData.getImageFile("Kodak_CX7530.jpg")
        val actual = photoService.getDate(file)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get date for corrupted exif EXPECT null`() {
        val file = TestData.getImageFile("image01137.jpg")
        val actual = photoService.getDate(file)

        Assertions.assertThat(actual).isNull()
    }

}