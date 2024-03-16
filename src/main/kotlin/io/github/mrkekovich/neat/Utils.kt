package io.github.mrkekovich.neat

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.unsafe.UnsafeNeuralNetwork
import io.github.mrkekovich.neat.unsafe.UnsafeTopology
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

@OptIn(MemoryUnsafe::class)
internal fun LongArray.toNeuralNetworkList(): List<UnsafeNeuralNetwork> = map { UnsafeNeuralNetwork(it) }
@OptIn(MemoryUnsafe::class)
internal fun LongArray.toTopologyList(): List<UnsafeTopology> = map { UnsafeTopology(it) }
