package xyz.pavelkorolev.bladerunner.services

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import xyz.pavelkorolev.bladerunner.entities.NamingStrategy
import xyz.pavelkorolev.bladerunner.utils.TestData
import java.text.SimpleDateFormat
import java.util.*

internal class NamingServiceImplTest {

    private val randomService: MockRandomService = MockRandomService()
    private val photoService: PhotoService = PhotoServiceImpl()
    private val fileService: FileService = FileServiceImpl()
    private val dateFormatter: DateFormatter = DateFormatterImpl()
    private val namingService: NamingService = NamingServiceImpl(
        photoService,
        fileService,
        randomService,
        dateFormatter
    )

    @Test
    fun `generate name for default strategy EXPECT same name`() {
        val expected = "plain-text.txt"

        val file = TestData.getTextFile("plain-text.txt")
        val actual = namingService.generateName(file, strategy = NamingStrategy.DEFAULT, addUuid = false)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `generate name for date modified strategy EXPECT date as name`() {
        val file = TestData.getTextFile("plain-text.txt")

        val dateModified = fileService.getModifyDate(file)
        val formatter = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
        val expectedDateString = formatter.format(dateModified)

        val expected = "$expectedDateString.txt"
        val actual = namingService.generateName(file, strategy = NamingStrategy.DATE_MODIFIED, addUuid = false)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `generate name for photo taken strategy EXPECT date taken as name`() {
        val moscow = TimeZone.getTimeZone("UTC")
        TimeZone.setDefault(moscow)
        val expected = "2008-05-30_15-56-01.jpg"

        val file = TestData.getImageFile("Canon_40D.jpg")
        val actual = namingService.generateName(file, strategy = NamingStrategy.PHOTO_TAKEN, addUuid = false)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `generate name for default strategy with uuid EXPECT same name with uuid`() {
        randomService.mockUuid = "uuid"

        val expected = "plain-text_uuid.txt"

        val file = TestData.getTextFile("plain-text.txt")
        val actual = namingService.generateName(file, strategy = NamingStrategy.DEFAULT, addUuid = true)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `generate name for default strategy with no extension EXPECT same name with no extension`() {
        val expected = "file"

        val file = TestData.getFile("file")
        val actual = namingService.generateName(file, strategy = NamingStrategy.DEFAULT, addUuid = false)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    private class MockRandomService : RandomService {

        var mockUuid: String = "mock-uuid"

        override fun generateUuidString(): String = mockUuid
    }

}