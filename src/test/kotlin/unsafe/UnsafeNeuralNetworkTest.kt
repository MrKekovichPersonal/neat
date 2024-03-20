package unsafe

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.unsafe.UnsafeNeuralNetwork
import io.github.mrkekovich.neat.unsafe.UnsafeTopology
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(MemoryUnsafe::class)
class UnsafeNeuralNetworkTest {
    private lateinit var neuralNetwork: UnsafeNeuralNetwork

    @BeforeEach
    fun setup() {
        val topology = UnsafeTopology("{}")
        neuralNetwork = UnsafeNeuralNetwork(topology)
        topology.close()
    }

    @AfterEach
    fun teardown() {
        neuralNetwork.close()
    }

    @Test
    fun `compute outputs from inputs`() {
        val inputs = doubleArrayOf(1.0, 2.0)
        neuralNetwork.compute(inputs)
    }
}