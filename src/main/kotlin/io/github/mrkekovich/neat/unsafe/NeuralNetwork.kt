package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.jni.NeuralNetworkUtils

/**
 * NeuralNetwork is an unsafe class that represents a neural network.
 * It provides methods for computing outputs from inputs and destroying the underlying native object.
 * If not closed, will stay in memory until the JVM shuts down, so be careful with it.
 *
 * @property pointer A long value representing the pointer to the native object.
 */
@MemoryUnsafe
@Suppress("unused")
class NeuralNetwork internal constructor(private var pointer: Long) : Destructible() {
    constructor(topology: Topology) : this(NeuralNetworkUtils.create(topology.pointer))

    /**
     * Computes the output values of the neural network given an array of input values.
     *
     * @param inputs A DoubleArray containing the input values for the neural network.
     * @return A DoubleArray containing the output values computed by the neural network.
     */
    fun compute(inputs: DoubleArray): DoubleArray = ensureOpen { NeuralNetworkUtils.compute(pointer, inputs) }

    override fun destroy() {
        NeuralNetworkUtils.destroy(pointer)
        pointer = 0
    }
}