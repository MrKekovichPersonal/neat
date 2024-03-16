package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.TrainerConfiguration
import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.exceptions.OutOfSpeciesException
import io.github.mrkekovich.neat.jni.TrainerUtils

/**
 * Trainer is an unsafe class that represents a NEAT (NeuroEvolution of Augmenting Topologies) trainer.
 * It provides methods for configuring the NEAT algorithm parameters, adding species, stepping through generations,
 * and retrieving the best neural networks and topologies.
 * If not closed, will stay in memory until the JVM shuts down, so be careful with it.
 *
 * @property pointer A long value representing the pointer to the native object.
 */
@MemoryUnsafe
@Suppress("unused")
class Trainer internal constructor(private var pointer: Long) : Destructible() {
    @Deprecated("Might be deleted due to lack of use")
    constructor(inputs: Long, outputs: Long) : this(TrainerUtils.create(inputs, outputs))

    constructor(configuration: TrainerConfiguration) : this(configuration.toTrainer())

    /**
     * The number of input neurons in the neural network.
     */
    val inputs: Long get() = ensureOpen { TrainerUtils.getInputs(pointer) }

    /**
     * The number of output neurons in the neural network.
     */
    val outputs: Long get() = ensureOpen { TrainerUtils.getOutputs(pointer) }

    /**
     * The maximum number of individuals (neural networks) per generation.
     */
    var maxIndividuals: Long
        get() = ensureOpen { TrainerUtils.getMaxIndividuals(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxIndividuals(pointer, value) }

    /**
     * The threshold at which two topologies are considered belonging to different species.
     */
    var deltaThreshold: Double
        get() = ensureOpen { TrainerUtils.getDeltaThreshold(pointer) }
        set(value) = ensureOpen { TrainerUtils.setDeltaThreshold(pointer, value) }

    /**
     * The coefficient for disjoint genes in the compatibility calculation formula.
     */
    var disjointCoefficient: Double
        get() = ensureOpen { TrainerUtils.getC1(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC1(pointer, value) }

    /**
     * The coefficient for excess genes in the compatibility calculation formula.
     */
    var excessCoefficient: Double
        get() = ensureOpen { TrainerUtils.getC2(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC2(pointer, value) }

    /**
     * The coefficient for weight differences in the compatibility calculation formula.
     */
    var weightDifferenceCoefficient: Double
        get() = ensureOpen { TrainerUtils.getC3(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC3(pointer, value) }

    /**
     * Sets the compatibility formula coefficients for the NEAT algorithm.
     * It is faster than setting each coefficient individually.
     *
     * The compatibility formula determines the compatibility score (delta) between two individuals
     * in the NEAT algorithm based on their genetic differences. It is defined as follows:
     *
     * ```
     * delta = (c1 * disjoints + c2 * excess) / (larger_topology_length - initial_size) + (mean(weight_distances) * c3)
     * ```
     *
     * @param disjointCoefficient Disjoint coefficient: Weight assigned to genes present in the first topology but not in the second.
     * @param excessCoefficient Excess coefficient: Weight assigned to genes present in the second topology but not in the first.
     * @param weightDifferenceCoefficient Weight difference coefficient: Weight assigned to genes with different weights in both topologies.
     */
    fun setCompatibilityFormula(
        disjointCoefficient: Double,
        excessCoefficient: Double,
        weightDifferenceCoefficient: Double,
    ): Unit = ensureOpen {
        TrainerUtils.setFormula(
            pointer,
            disjointCoefficient,
            excessCoefficient,
            weightDifferenceCoefficient
        )
    }

    /**
     * The probability of changing weights during mutation.
     */
    var weightChangeProbability: Double
        get() = ensureOpen { TrainerUtils.getChangeWeights(pointer) }
        set(value) = ensureOpen { TrainerUtils.setChangeWeights(pointer, value) }

    /**
     * The probability of adding a new neuron during mutation.
     */
    var newNeuronProbability: Double
        get() = ensureOpen { TrainerUtils.getGuaranteedNewNeuron(pointer) }
        set(value) = ensureOpen { TrainerUtils.setGuaranteedNewNeuron(pointer, value) }

    /**
     * The maximum number of hidden layers allowed in a neural network.
     */
    var maxLayers: Long
        get() = ensureOpen { TrainerUtils.getMaxLayers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxLayers(pointer, value) }

    /**
     * The maximum number of neurons allowed per hidden layer in a neural network.
     */
    var maxPerLayers: Long
        get() = ensureOpen { TrainerUtils.getMaxPerLayers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxPerLayers(pointer, value) }

    /**
     * Determines if crossovers (breeding) are enabled.
     */
    var enableCrossovers: Boolean
        get() = ensureOpen { TrainerUtils.getCrossovers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setCrossovers(pointer, value) }

    val maxSpeciesCount: Long get() = ensureOpen { TrainerUtils.getMaxSpeciesCount(pointer) }

    /**
     * Retrieves a list of new neural networks generated by the NEAT algorithm.
     *
     * @return A list of NeuralNetwork objects representing the new neural networks.
     * @throws OutOfSpeciesException if there are no species left in the algorithm.
     */
    fun getNewNetworks(): List<NeuralNetwork> = ensureOpen {
        TrainerUtils.getNewNetworks(pointer).takeIf { it.isNotEmpty() }?.toNeuralNetworkList()
            ?: throw OutOfSpeciesException()
    }

    /**
     * Retrieves a list of the best topologies (structures) found by the NEAT algorithm.
     *
     * @return A list of Topology objects representing the best topologies.
     */
    fun getBestTopologies(): List<Topology> = ensureOpen { TrainerUtils.getBestTopologies(pointer).toTopologyList() }

    /**
     * Adds a new species with the given topology to the NEAT algorithm.
     *
     * @param topology The Topology object representing the initial topology for the new species.
     */
    fun addSpecieWithTopology(topology: Topology): Unit = ensureOpen {
        TrainerUtils.addSpecieWithTopology(pointer, topology.pointer)
    }


    /**
     * Steps through one generation of the NEAT algorithm using the provided scores.
     *
     * @param scores A DoubleArray containing the fitness scores for the current generation.
     * Must be ordered in the same way as new networks were given.
     * @return A list of NeuralNetwork objects representing the new neural networks generated in the next generation.
     * @throws OutOfSpeciesException if there are no species left in the algorithm.
     */
    fun step(scores: DoubleArray): List<NeuralNetwork> = ensureOpen {
        TrainerUtils.step(pointer, scores).takeIf { it.isNotEmpty() }?.toNeuralNetworkList()
            ?: throw OutOfSpeciesException()
    }

    /**
     * Creates a new specie with a new topology.
     */
    fun createNewSpecie(): Unit = ensureOpen { TrainerUtils.createNewSpecie(pointer) }

    @Deprecated("Might be deleted due to lack of use")
    fun finish(): Unit = ensureOpen { TrainerUtils.finish(pointer) }

    override fun destroy() {
        TrainerUtils.destroy(pointer)
        pointer = 0
    }
}

@OptIn(MemoryUnsafe::class)
private fun LongArray.toNeuralNetworkList(): List<NeuralNetwork> = map { NeuralNetwork(it) }

@OptIn(MemoryUnsafe::class)
private fun LongArray.toTopologyList(): List<Topology> = map { Topology(it) }

private fun TrainerConfiguration.toTrainer(): Long = TrainerUtils.createWithAllParameters(
    inputs = inputs,
    outputs = outputs,
    maxIndividuals = maxIndividuals,
    maxLayers = maxLayers,
    maxPerLayer = maxPerLayer,
    deltaThreshold = deltaThreshold,
    disjointCoefficient = disjointCoefficient,
    excessCoefficient = excessCoefficient,
    weightDifferenceCoefficient = weightDifferenceCoefficient,
    enableCrossovers = enableCrossovers,
    weightChangeProbability = weightChangeProbability,
    newNeuronProbability = newNeuronProbability,
)