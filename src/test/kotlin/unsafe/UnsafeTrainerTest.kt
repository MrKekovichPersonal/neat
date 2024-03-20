package unsafe

import io.github.mrkekovich.neat.TrainerConfiguration
import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.exceptions.OutOfSpeciesException
import io.github.mrkekovich.neat.unsafe.UnsafeTrainer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@OptIn(MemoryUnsafe::class)
class UnsafeTrainerTest {
    private lateinit var trainer: UnsafeTrainer

    @BeforeEach
    fun setup() {
        val config = TrainerConfiguration(inputs = 2, outputs = 1)
        trainer = UnsafeTrainer(config)
    }

    @AfterEach
    fun teardown() {
        trainer.close()
    }

    @Test
    fun `create trainer with configuration`() {
        assertEquals(2, trainer.inputs)
        assertEquals(1, trainer.outputs)
        assertEquals(100, trainer.maxIndividuals)
        assertEquals(4, trainer.maxLayers)
        assertEquals(20, trainer.maxPerLayers)
        assertEquals(3.0, trainer.deltaThreshold)
        assertEquals(1.0, trainer.disjointCoefficient)
        assertEquals(1.0, trainer.excessCoefficient)
        assertEquals(1.0, trainer.weightDifferenceCoefficient)
        assertTrue(trainer.enableCrossovers)
        assertEquals(0.95, trainer.weightChangeProbability)
        assertEquals(0.2, trainer.newNeuronProbability)
    }

    @Test
    fun `set compatibility formula`() {
        trainer.setCompatibilityFormula(1.5, 2.0, 0.5)
        assertEquals(1.5, trainer.disjointCoefficient)
        assertEquals(2.0, trainer.excessCoefficient)
        assertEquals(0.5, trainer.weightDifferenceCoefficient)
    }

    @Test
    fun `get new networks throws exception when no species`() {
        assertThrows<OutOfSpeciesException> {
            trainer.getNewNetworks()
        }
    }

    @Test
    fun `get new networks returns list of networks`() {
        trainer.createNewSpecie()
        trainer.getNewNetworks().also {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun `step throws exception when no species`() {
        assertThrows<OutOfSpeciesException> {
            trainer.step(doubleArrayOf(1.0))
        }
    }

    @Test
    fun `training pipeline works`() {
        trainer.createNewSpecie()

        repeat(10) {
            trainer.getNewNetworks().size
                .let { DoubleArray(it) }
                .let { dummyScores -> trainer.step(dummyScores) }
        }
    }
}
