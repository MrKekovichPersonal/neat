package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.TrainerConfiguration
import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.exceptions.OutOfSpeciesException
import io.github.mrkekovich.neat.interfaces.Trainer
import io.github.mrkekovich.neat.jni.TrainerUtils

/**
 * UnsafeTrainer is an unsafeNeuralNetwork class that represents a NEAT (NeuroEvolution of Augmenting Topologies) trainer.
 * It provides methods for configuring the NEAT algorithm parameters, adding species, stepping through generations,
 * and retrieving the best neural networks and topologies.
 * If not closed, will stay in memory until the JVM shuts down, so be careful with it.
 *
 * @property pointer A long value representing the pointer to the native object.
 */
@MemoryUnsafe
@Suppress("unused")
class UnsafeTrainer internal constructor(private var pointer: Long) : Trainer, Destructible() {
    @Deprecated("Might be deleted due to lack of use")
    constructor(inputs: Long, outputs: Long) : this(TrainerUtils.create(inputs, outputs))

    constructor(configuration: TrainerConfiguration) : this(configuration.toTrainer())

    override val inputs: Long get() = ensureOpen { TrainerUtils.getInputs(pointer) }

    override val outputs: Long get() = ensureOpen { TrainerUtils.getOutputs(pointer) }

    override var maxIndividuals: Long
        get() = ensureOpen { TrainerUtils.getMaxIndividuals(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxIndividuals(pointer, value) }

    override var deltaThreshold: Double
        get() = ensureOpen { TrainerUtils.getDeltaThreshold(pointer) }
        set(value) = ensureOpen { TrainerUtils.setDeltaThreshold(pointer, value) }

    override var disjointCoefficient: Double
        get() = ensureOpen { TrainerUtils.getC1(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC1(pointer, value) }

    override var excessCoefficient: Double
        get() = ensureOpen { TrainerUtils.getC2(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC2(pointer, value) }

    override var weightDifferenceCoefficient: Double
        get() = ensureOpen { TrainerUtils.getC3(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC3(pointer, value) }

    override fun setCompatibilityFormula(
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

    override var weightChangeProbability: Double
        get() = ensureOpen { TrainerUtils.getChangeWeights(pointer) }
        set(value) = ensureOpen { TrainerUtils.setChangeWeights(pointer, value) }

    override var newNeuronProbability: Double
        get() = ensureOpen { TrainerUtils.getGuaranteedNewNeuron(pointer) }
        set(value) = ensureOpen { TrainerUtils.setGuaranteedNewNeuron(pointer, value) }

    override var maxLayers: Long
        get() = ensureOpen { TrainerUtils.getMaxLayers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxLayers(pointer, value) }

    override var maxPerLayers: Long
        get() = ensureOpen { TrainerUtils.getMaxPerLayers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxPerLayers(pointer, value) }

    override var enableCrossovers: Boolean
        get() = ensureOpen { TrainerUtils.getCrossovers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setCrossovers(pointer, value) }

    override val maxSpeciesCount: Long get() = ensureOpen { TrainerUtils.getMaxSpeciesCount(pointer) }

    override fun getNewNetworks(): List<UnsafeNeuralNetwork> = ensureOpen {
        TrainerUtils.getNewNetworks(pointer).takeIf { it.isNotEmpty() }?.toNeuralNetworkList()
            ?: throw OutOfSpeciesException()
    }

    override fun getBestTopologies(): List<UnsafeTopology> = ensureOpen { TrainerUtils.getBestTopologies(pointer).toTopologyList() }

    /**
     * Adds a new species with the given unsafeTopology to the NEAT algorithm.
     *
     * @param unsafeTopology The UnsafeTopology object representing the initial unsafeTopology for the new species.
     */
    fun addSpecieWithTopology(unsafeTopology: UnsafeTopology): Unit = ensureOpen {
        TrainerUtils.addSpecieWithTopology(pointer, unsafeTopology.pointer)
    }


    override fun step(scores: DoubleArray): List<UnsafeNeuralNetwork> = ensureOpen {
        TrainerUtils.step(pointer, scores).takeIf { it.isNotEmpty() }?.toNeuralNetworkList()
            ?: throw OutOfSpeciesException()
    }

    override fun createNewSpecie(): Unit = ensureOpen { TrainerUtils.createNewSpecie(pointer) }

    @Deprecated("Might be deleted due to lack of use")
    fun finish(): Unit = ensureOpen { TrainerUtils.finish(pointer) }

    override fun destroy() {
        TrainerUtils.destroy(pointer)
        pointer = 0
    }
}

@OptIn(MemoryUnsafe::class)
private fun LongArray.toNeuralNetworkList(): List<UnsafeNeuralNetwork> = map { UnsafeNeuralNetwork(it) }

@OptIn(MemoryUnsafe::class)
private fun LongArray.toTopologyList(): List<UnsafeTopology> = map { UnsafeTopology(it) }

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