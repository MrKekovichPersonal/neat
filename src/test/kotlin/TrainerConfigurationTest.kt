import io.github.mrkekovich.neat.TrainerConfiguration
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrainerConfigurationTest {
    @Test
    fun `create trainer configuration with default values`() {
        val config = TrainerConfiguration(inputs = 2, outputs = 1)
        assertEquals(2, config.inputs)
        assertEquals(1, config.outputs)
        assertEquals(100, config.maxIndividuals)
        assertEquals(4, config.maxLayers)
        assertEquals(20, config.maxPerLayer)
        assertEquals(3.0, config.deltaThreshold)
        assertEquals(1.0, config.disjointCoefficient)
        assertEquals(1.0, config.excessCoefficient)
        assertEquals(1.0, config.weightDifferenceCoefficient)
        assertTrue(config.enableCrossovers)
        assertEquals(0.95, config.weightChangeProbability)
        assertEquals(0.2, config.newNeuronProbability)
    }

    @Test
    fun `create trainer configuration with custom values`() {
        val config = TrainerConfiguration(
            inputs = 10,
            outputs = 5,
            maxIndividuals = 200,
            maxLayers = 6,
            maxPerLayer = 30,
            deltaThreshold = 2.5,
            disjointCoefficient = 1.5,
            excessCoefficient = 2.0,
            weightDifferenceCoefficient = 0.5,
            enableCrossovers = false,
            weightChangeProbability = 0.8,
            newNeuronProbability = 0.3
        )
        assertEquals(10, config.inputs)
        assertEquals(5, config.outputs)
        assertEquals(200, config.maxIndividuals)
        assertEquals(6, config.maxLayers)
        assertEquals(30, config.maxPerLayer)
        assertEquals(2.5, config.deltaThreshold)
        assertEquals(1.5, config.disjointCoefficient)
        assertEquals(2.0, config.excessCoefficient)
        assertEquals(0.5, config.weightDifferenceCoefficient)
        assertFalse(config.enableCrossovers)
        assertEquals(0.8, config.weightChangeProbability)
        assertEquals(0.3, config.newNeuronProbability)
    }
}