package io.github.mrkekovich.neat

import io.github.mrkekovich.neat.jni.NeuralNetworkUtils

class NeuralNetwork internal constructor(private var pointer: Long) : AutoCloseable {
    var isOpen = true
        private set

    private inline fun <R> ensureOpen(block: () -> R): R =
        if (isOpen) block()
        else throw IllegalStateException("NeuralNetwork was closed.")

    companion object {
        internal fun LongArray.toNeuralNetworks(): List<NeuralNetwork> = map { NeuralNetwork(it) }
    }

    fun compute(inputs: DoubleArray): DoubleArray = ensureOpen {
        NeuralNetworkUtils.compute(pointer, inputs)
    }

    fun computeAndDestroy(inputs: DoubleArray): DoubleArray = ensureOpen {
        compute(inputs).also {
            NeuralNetworkUtils.destroy(pointer)
        }
    }

    override fun close() {
        isOpen = false
        NeuralNetworkUtils.destroy(pointer)
        pointer = 0L
    }
}