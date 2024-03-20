package io.github.mrkekovich.neat

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.interfaces.NeuralNetwork
import io.github.mrkekovich.neat.unsafe.UnsafeNeuralNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(MemoryUnsafe::class)
@Suppress("unused")
abstract class Simulation {
    @Volatile
    private var _networks = listOf<UnsafeNeuralNetwork>()

    val networks: List<NeuralNetwork> get() = _networks

    /**
     * Returns the fitness scores corresponding to each neural network in the simulation.
     */
    abstract suspend fun getScores(): DoubleArray

    /**
     * Called on each iteration of the NEAT algorithm after the neural networks have been updated.
     */
    open suspend fun resetCallback() = Unit

    /**
     * Resets the simulation to a new set of neural networks safely.
     *
     * @param newNetworks The new set of neural networks.
     */
    @MemoryUnsafe // can lead to crashes if closed NNs were passed in.
    suspend fun closeAndReset(newNetworks: List<UnsafeNeuralNetwork>) = coroutineScope {
        for (network in _networks) launch { network.close() }
        _networks = newNetworks
        resetCallback()
    }
}