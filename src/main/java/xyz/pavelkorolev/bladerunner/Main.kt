package xyz.pavelkorolev.bladerunner

import com.github.ajalt.clikt.core.subcommands
import xyz.pavelkorolev.bladerunner.commands.CleanCommand
import xyz.pavelkorolev.bladerunner.commands.FindCommand
import xyz.pavelkorolev.bladerunner.commands.RunCommand
import xyz.pavelkorolev.bladerunner.commands.MainCommand
import xyz.pavelkorolev.bladerunner.services.*

private val hashService: HashService = HashServiceImpl()

private val runnerService: RunnerService = RunnerServiceImpl(hashService)

private val photoService: PhotoService = PhotoServiceImpl()

private val fileService: FileService = FileServiceImpl()

private val namingService: NamingService = NamingServiceImpl(photoService, fileService)

fun main(args: Array<String>) {
    val findCommand = FindCommand(runnerService)
    val cleanCommand = CleanCommand(runnerService)
    val runCommand = RunCommand(runnerService, namingService)

    MainCommand()
        .subcommands(
            findCommand,
            cleanCommand,
            runCommand
        )
        .main(args)
}
