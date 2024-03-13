package io.github.mrkekovich.neat

interface Simulation {
    var neuralNetworks: List<NeuralNetwork>

    fun runGeneration(): DoubleArray

    fun resetNeuralNetworks(networks: List<NeuralNetwork>) {
        neuralNetworks = networks
    }
}