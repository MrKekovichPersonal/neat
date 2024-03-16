import io.github.mrkekovich.neat.TrainerConfiguration
import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.unsafe.Trainer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrainerTest {
    @OptIn(MemoryUnsafe::class)
    @Test
    fun create() {
        val trainer = Trainer(1, 1)
        assertEquals(1, trainer.inputs)
        assertEquals(1, trainer.outputs)

        val trainerWithConfig = Trainer(
            TrainerConfiguration(
                1, 1,
                disjointCoefficient = 1.0,
                excessCoefficient = 1.0,
                weightDifferenceCoefficient = 1.0,
                weightChangeProbability = 0.95
            )
        )
        assertEquals(1, trainerWithConfig.inputs)
        assertEquals(1, trainerWithConfig.outputs)
        assertEquals(1.0, trainerWithConfig.disjointCoefficient)
        assertEquals(1.0, trainerWithConfig.excessCoefficient)
        assertEquals(1.0, trainerWithConfig.weightDifferenceCoefficient)
        assertEquals(0.95, trainerWithConfig.weightChangeProbability)
    }
}
