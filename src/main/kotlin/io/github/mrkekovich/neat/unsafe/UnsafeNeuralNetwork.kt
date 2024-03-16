package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.jni.NeuralNetworkUtils

@MemoryUnsafe
class UnsafeNeuralNetwork internal constructor(private var pointer: Long) : AutoCloseable {
    constructor(topology: UnsafeTopology) : this(NeuralNetworkUtils.create(topology.pointer))

    var isOpen = true
        private set

    private inline fun <R> ensureOpen(block: () -> R): R =
        if (isOpen) block()
        else throw IllegalStateException("UnsafeNeuralNetwork was closed.")

    fun compute(inputs: DoubleArray): DoubleArray = ensureOpen {
        NeuralNetworkUtils.compute(pointer, inputs)
    }

    override fun close() {
        isOpen = false
        NeuralNetworkUtils.destroy(pointer)
        pointer = 0L
    }
}