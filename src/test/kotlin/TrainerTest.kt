import io.github.mrkekovich.neat.Trainer
import io.github.mrkekovich.neat.TrainerConfiguration
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrainerTest {
    @Test
    fun create() {
        val config = TrainerConfiguration(
            1, 1,
            c1 = 1.0,
            c2 = 1.0,
            c3 = 1.0,
            changeWeights = 0.95
        )
        val trainer = Trainer(1, 1)
        assertEquals(1, trainer.inputs)
        assertEquals(1, trainer.outputs)

        val trainerWithConfig = Trainer(config)
        assertEquals(1, trainerWithConfig.inputs)
        assertEquals(1, trainerWithConfig.outputs)
        assertEquals(1.0, trainerWithConfig.c1)
        assertEquals(1.0, trainerWithConfig.c2)
        assertEquals(1.0, trainerWithConfig.c3)
        assertEquals(0.95, trainerWithConfig.changeWeights)
    }
}