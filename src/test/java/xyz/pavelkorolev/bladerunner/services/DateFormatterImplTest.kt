package xyz.pavelkorolev.bladerunner.services

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat

internal class DateFormatterImplTest {

    private val dateFormatter: DateFormatter = DateFormatterImpl()

    @Test
    fun `format EXPECT string formatted date`() {
        val formatter = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
        val expected = "2020-10-05_22-00-48"
        val date = formatter.parse(expected)
        val actual = dateFormatter.formatDate(date)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

}