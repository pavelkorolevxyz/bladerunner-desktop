package xyz.pavelkorolev.bladerunner.services

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import xyz.pavelkorolev.bladerunner.utils.TestData
import java.io.File

internal class RunnerServiceImplTest {

    private val hashService: HashService = HashServiceImpl()
    private val runnerService: RunnerService = RunnerServiceImpl(hashService)

    @Test
    fun `process files EXPECT same count as total files count and completion`() {
        val expectedOkCount = 5
        val expectedCloneCount = 2
        val expectedTotalCount = expectedOkCount + expectedCloneCount

        val listener = object : RunnerListener {
            var okCount = 0
            var cloneCount = 0
            var totalCount = 0
            var isCompleted = false

            override fun onFileOk(file: File) {
                okCount++
                totalCount++
            }

            override fun onFileClone(file: File, original: File) {
                cloneCount++
                totalCount++
            }

            override fun onCompleted() {
                isCompleted = true
            }
        }
        runnerService.processFiles(TestData.directory, listener)

        Assertions.assertThat(listener.totalCount).isEqualTo(expectedTotalCount)
        Assertions.assertThat(listener.okCount).isEqualTo(expectedOkCount)
        Assertions.assertThat(listener.cloneCount).isEqualTo(expectedCloneCount)
        Assertions.assertThat(listener.isCompleted).isTrue()
    }

    @Test
    fun `get total file count EXPECT files count without directories`() {
        val expected = 7
        val actual = runnerService.getTotalFileCount(TestData.directory)
        Assertions.assertThat(actual).isEqualTo(expected)
    }

}