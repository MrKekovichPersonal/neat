package io.github.mrkekovich.neat

import java.io.File

internal fun loadLib(fileName: String = "neat_gru") {
    val inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("$fileName.dll")
    val tempFile = File.createTempFile(fileName, ".dll")
    tempFile.deleteOnExit()

    inputStream.use { input ->
        tempFile.outputStream().use { output ->
            input?.copyTo(output) ?: throw IllegalStateException("Can't load $fileName.dll")
        }
    }

    System.load(tempFile.absolutePath)
}

fun runSimulation(
    simulation: Simulation,
    trainer: Trainer,
    iterations: Int,
) {
    for (i in 0 until iterations) {
        simulation.runGeneration()
            .let { trainer.step(it) }
            .also { simulation.resetNeuralNetworks(it) }
    }
}

inline fun runSimulation(
    simulation: Simulation,
    trainer: Trainer,
    iterations: Int,
    accessCallback: (TrainAccessCallback) -> Unit
) {
    for (i in 0 until iterations) {
        simulation.runGeneration()
            .let { trainer.step(it) }
            .also { simulation.resetNeuralNetworks(it) }
        TrainAccessCallback(simulation, trainer, i).also { accessCallback(it) }
    }
}

data class TrainAccessCallback(
    val simulation: Simulation,
    val trainer: Trainer,
    val iterations: Int,
)
