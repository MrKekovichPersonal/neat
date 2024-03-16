@file:Suppress("unused")

package io.github.mrkekovich.neat

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.exceptions.OutOfSpeciesException
import io.github.mrkekovich.neat.interfaces.Trainer
import io.github.mrkekovich.neat.unsafe.UnsafeTrainer
import io.github.mrkekovich.neat.wrappers.TopologyWrapper

/**
 * Trains neural networks using the NEAT algorithm for a specified number of iterations.
 *
 * @param iterations The number of iterations (generations) to train the networks.
 * @param trainerConfiguration Configuration parameters for the NEAT trainer.
 * @param simulation The simulation environment used to evaluate networks.
 * @param access Callback function invoked after each iteration, providing access to the trainer and iteration count.
 * @return A list of the best topologies.
 */
@OptIn(MemoryUnsafe::class)
suspend fun train(
    iterations: Int,
    trainerConfiguration: TrainerConfiguration,
    simulation: Simulation,
    access: (trainer: Trainer, iteration: Int) -> Unit,
): List<TopologyWrapper> {
    return UnsafeTrainer(trainerConfiguration)
        .use { trainer ->
            simulation.safeReset(trainer.getNewNetworks())

            try {
                repeat(iterations) {
                    val newNetworks = trainer.step(simulation.getScores())
                    simulation.safeReset(newNetworks)
                    access(trainer, it)
                }
            } catch (_: OutOfSpeciesException) {
                println("Species were exhausted. Returning best topologies.")
            }

            trainer.getBestTopologies().map { TopologyWrapper(it) }
        }
}

/**
 * Trains neural networks using the NEAT algorithm for a specified number of iterations.
 *
 * @param iterations The number of iterations (generations) to train the networks.
 * @param trainerConfiguration Configuration parameters for the NEAT trainer.
 * @param simulation The simulation environment used to evaluate networks.
 * @return A list of wrapper objects containing the best evolved neural network topologies.
 */
// repeating code for code optimization.
@OptIn(MemoryUnsafe::class)
suspend fun train(
    iterations: Int,
    trainerConfiguration: TrainerConfiguration,
    simulation: Simulation,
): List<TopologyWrapper> {
    return UnsafeTrainer(trainerConfiguration).use { trainer ->
        simulation.safeReset(trainer.getNewNetworks())

        try {
            repeat(iterations) {
                val newNetworks = trainer.step(simulation.getScores())
                simulation.safeReset(newNetworks)
            }
        } catch (e: OutOfSpeciesException) {
            println("Species were exhausted. Returning the best topologies.")
        }

        trainer.getBestTopologies().map { TopologyWrapper(it) }
    }
}
