package io.github.mrkekovich.neat.interfaces

fun interface NeuralNetwork {
    /**
     * Computes the output values of the neural network given an array of input values.
     *
     * @param inputs A DoubleArray containing the input values for the neural network.
     * @return A DoubleArray containing the output values computed by the neural network.
     */
    fun compute(inputs: DoubleArray): DoubleArray
}