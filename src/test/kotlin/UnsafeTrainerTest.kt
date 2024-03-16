import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.unsafe.UnsafeTrainer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UnsafeTrainerTest {
    @OptIn(MemoryUnsafe::class)
    @Test
    fun create() {
        val unsafeTrainer = UnsafeTrainer(1, 1)
        assertEquals(1, unsafeTrainer.inputs)
        assertEquals(1, unsafeTrainer.outputs)

        val unsafeTrainerWithConfig = UnsafeTrainer(
            1, 1,
            c1 = 1.0,
            c2 = 1.0,
            c3 = 1.0,
            changeWeights = 0.95
        )
        assertEquals(1, unsafeTrainerWithConfig.inputs)
        assertEquals(1, unsafeTrainerWithConfig.outputs)
        assertEquals(1.0, unsafeTrainerWithConfig.c1)
        assertEquals(1.0, unsafeTrainerWithConfig.c2)
        assertEquals(1.0, unsafeTrainerWithConfig.c3)
        assertEquals(0.95, unsafeTrainerWithConfig.changeWeights)
    }

    @OptIn(MemoryUnsafe::class)
    @Test
    fun train() {
        val unsafeTrainer = UnsafeTrainer(1, 1)
        var networks = unsafeTrainer.getNewNetworks()

        for (gen in 0..10) {
            val score = networks.map { net ->
                net.use { it.compute(doubleArrayOf(0.0))[0] }
            }.toDoubleArray()
            networks = unsafeTrainer.step(score)
        }

        unsafeTrainer.getBestTopologies().forEach {
            println("=====================================================")
            println(it.toJson())
        }
    }
}
