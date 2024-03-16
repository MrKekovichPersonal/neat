package io.github.mrkekovich.neat

import java.io.File

/**
 * Loads the specified native library.
 */
internal fun loadLib(fileName: String = "neat_gru") { // TODO: Add support for .so files
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
