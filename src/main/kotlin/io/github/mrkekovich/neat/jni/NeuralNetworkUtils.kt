package io.github.mrkekovich.neat.jni

import io.github.mrkekovich.neat.loadLib


object NeuralNetworkUtils {
    external fun compute(neuralNetworkPointer: Long, inputs: DoubleArray): DoubleArray

    init { loadLib() }
}