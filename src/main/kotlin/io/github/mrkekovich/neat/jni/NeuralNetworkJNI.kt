package io.github.mrkekovich.neat.jni

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.loadLib

/**
 * JNI bindings for native implementation of neural network.
 */
@MemoryUnsafe
object NeuralNetworkJNI {
    @JvmStatic external fun create(topologyPointer: Long): Long
    @JvmStatic external fun compute(neuralNetworkPointer: Long, inputs: DoubleArray): DoubleArray
    @JvmStatic external fun destroy(neuralNetworkPointer: Long)
    init { loadLib() }
}