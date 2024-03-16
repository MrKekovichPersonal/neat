package io.github.mrkekovich.neat.wrappers

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.interfaces.NeuralNetwork
import io.github.mrkekovich.neat.unsafe.UnsafeNeuralNetwork

@OptIn(MemoryUnsafe::class)
class NeuralNetworkWrapper(val unsafeNeuralNetwork: UnsafeNeuralNetwork) {
    constructor(topology: TopologyWrapper) : this(UnsafeNeuralNetwork(topology.unsafeTopology))

    inline fun <R> use(block: (NeuralNetwork) -> R): R = unsafeNeuralNetwork.use(block)
}