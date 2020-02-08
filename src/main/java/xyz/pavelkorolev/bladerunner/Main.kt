package xyz.pavelkorolev.bladerunner

import com.github.ajalt.clikt.core.subcommands
import xyz.pavelkorolev.bladerunner.commands.FindCommand
import xyz.pavelkorolev.bladerunner.commands.CleanCommand
import xyz.pavelkorolev.bladerunner.commands.MainCommand
import xyz.pavelkorolev.bladerunner.services.HashService
import xyz.pavelkorolev.bladerunner.services.HashServiceImpl
import xyz.pavelkorolev.bladerunner.services.RunnerService
import xyz.pavelkorolev.bladerunner.services.RunnerServiceImpl

private val hashService: HashService = HashServiceImpl()

private val runnerService: RunnerService = RunnerServiceImpl(hashService)

fun main(args: Array<String>) {
    val findCommand = FindCommand(runnerService)
    val killCommand = CleanCommand(runnerService)

    MainCommand()
        .subcommands(
            findCommand,
            killCommand
        )
        .main(args)
}
