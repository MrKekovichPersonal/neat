package io.github.mrkekovich.neat

/**
 * Configuration data class for NEAT UnsafeTrainer.
 * Defines parameters for the NEAT algorithm, which evolves neural networks.
 *
 * @property inputs Number of input neurons in the neural network.
 * @property outputs Number of output neurons in the neural network.
 * @property maxIndividuals Maximum number of individuals (neural networks) per generation.
 * @property maxLayers Maximum number of hidden layers allowed in a neural network.
 * @property maxPerLayer Maximum number of neurons allowed per hidden layer in a neural network.
 * @property deltaThreshold Threshold at which two topologies are considered belonging to different species.
 * @property disjointCoefficient Coefficient for disjoint genes in the compatibility calculation formula.
 * @property excessCoefficient Coefficient for excess genes in the compatibility calculation formula.
 * @property weightDifferenceCoefficient Coefficient for weight differences in the compatibility calculation formula.
 * @property enableCrossovers Determines if crossovers (breeding) are enabled.
 * @property weightChangeProbability Probability of changing weights during mutation.
 * @property newNeuronProbability Probability of adding a new neuron during mutation.
 */
data class TrainerConfiguration(
    val inputs: Long,
    val outputs: Long,
    val maxIndividuals: Long = 100,
    val maxLayers: Long = 4,
    val maxPerLayer: Long = 20,
    val deltaThreshold: Double = 3.0,
    val disjointCoefficient: Double = 1.0,
    val excessCoefficient: Double = 1.0,
    val weightDifferenceCoefficient: Double = 1.0,
    val enableCrossovers: Boolean = true,
    val weightChangeProbability: Double = 0.95,
    val newNeuronProbability: Double = 0.2,
)