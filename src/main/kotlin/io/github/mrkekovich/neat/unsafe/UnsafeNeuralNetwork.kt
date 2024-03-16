package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.interfaces.NeuralNetwork
import io.github.mrkekovich.neat.jni.NeuralNetworkUtils

/**
 * UnsafeNeuralNetwork is an unsafeNeuralNetwork class that represents a neural network.
 * It provides methods for computing outputs from inputs and destroying the underlying native object.
 * If not closed, will stay in memory until the JVM shuts down, so be careful with it.
 *
 * @property pointer A long value representing the pointer to the native object.
 */
@MemoryUnsafe
@Suppress("unused")
class UnsafeNeuralNetwork internal constructor(private var pointer: Long) : NeuralNetwork, Destructible() {
    constructor(unsafeTopology: UnsafeTopology) : this(NeuralNetworkUtils.create(unsafeTopology.pointer))

    override fun compute(inputs: DoubleArray): DoubleArray = ensureOpen { NeuralNetworkUtils.compute(pointer, inputs) }

    override fun destroy() {
        NeuralNetworkUtils.destroy(pointer)
        pointer = 0
    }
}