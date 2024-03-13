package io.github.mrkekovich.neat

import io.github.mrkekovich.neat.jni.NeuralNetworkUtils

class NeuralNetwork internal constructor(private val pointer: Long) {
    companion object {
        internal fun LongArray.toNeuralNetworks(): List<NeuralNetwork> = map { NeuralNetwork(it) }
    }

    fun compute(inputs: DoubleArray): DoubleArray = NeuralNetworkUtils.compute(pointer, inputs)
}