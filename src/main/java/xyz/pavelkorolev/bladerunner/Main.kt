package xyz.pavelkorolev.bladerunner

import xyz.pavelkorolev.bladerunner.commands.RunCommand
import xyz.pavelkorolev.bladerunner.services.HashService
import xyz.pavelkorolev.bladerunner.services.HashServiceImpl
import xyz.pavelkorolev.bladerunner.services.RunnerService
import xyz.pavelkorolev.bladerunner.services.RunnerServiceImpl

private val hashService: HashService = HashServiceImpl()

private val runnerService: RunnerService = RunnerServiceImpl(hashService)

fun main(args: Array<String>) {
    RunCommand(runnerService)
        .main(args)
}
