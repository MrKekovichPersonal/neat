package io.github.mrkekovich.neat.jni

import io.github.mrkekovich.neat.loadLib

/**
 * JNI bindings for native implementation of neural network.
 */
internal object NeuralNetworkUtils {
    external fun create(topologyPointer: Long): Long
    external fun compute(neuralNetworkPointer: Long, inputs: DoubleArray): DoubleArray
    external fun destroy(neuralNetworkPointer: Long)
    init { loadLib() }
}