import io.github.mrkekovich.neat.TrainerConfiguration
import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.unsafe.UnsafeTrainer
import io.github.mrkekovich.neat.wrappers.NeuralNetworkWrapper
import io.github.mrkekovich.neat.wrappers.TopologyWrapper
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class UnsafeTrainerTestWrapper {
    @OptIn(MemoryUnsafe::class)
    @Test
    fun create() {
        val unsafeTrainer = UnsafeTrainer(1, 1)
        assertEquals(1, unsafeTrainer.inputs)
        assertEquals(1, unsafeTrainer.outputs)

        val unsafeTrainerWithConfig = UnsafeTrainer(
            TrainerConfiguration(
                1, 1,
                disjointCoefficient = 1.0,
                excessCoefficient = 1.0,
                weightDifferenceCoefficient = 1.0,
                weightChangeProbability = 0.95
            )
        )
        assertEquals(1, unsafeTrainerWithConfig.inputs)
        assertEquals(1, unsafeTrainerWithConfig.outputs)
        assertEquals(1.0, unsafeTrainerWithConfig.disjointCoefficient)
        assertEquals(1.0, unsafeTrainerWithConfig.excessCoefficient)
        assertEquals(1.0, unsafeTrainerWithConfig.weightDifferenceCoefficient)
        assertEquals(0.95, unsafeTrainerWithConfig.weightChangeProbability)
    }

    @Test
    fun smth() {
        NeuralNetworkWrapper(TopologyWrapper(File(""))).use {

        }
    }
}
