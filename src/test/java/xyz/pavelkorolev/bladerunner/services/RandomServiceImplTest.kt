package xyz.pavelkorolev.bladerunner.services

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class RandomServiceImplTest {

    private val randomService: RandomService = RandomServiceImpl()

    @Test
    fun `generate EXPECT different result`() {
        val first = randomService.generateUuidString()
        val second = randomService.generateUuidString()

        Assertions.assertThat(first).isNotEqualTo(second)
    }

}