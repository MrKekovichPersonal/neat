package io.github.mrkekovich.neat

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.jni.TrainerUtils
import io.github.mrkekovich.neat.unsafe.NeuralNetwork
import io.github.mrkekovich.neat.unsafe.Topology
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
