package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.jni.NeuralNetworkUtils

@MemoryUnsafe
@Suppress("unused")
class NeuralNetwork internal constructor(private var pointer: Long) : Destructible() {
    constructor(topology: Topology) : this(NeuralNetworkUtils.create(topology.pointer))

    fun compute(inputs: DoubleArray): DoubleArray = ensureOpen {
        NeuralNetworkUtils.compute(pointer, inputs)
    }

    override fun destroy() {
        NeuralNetworkUtils.destroy(pointer)
        pointer = 0
    }
}