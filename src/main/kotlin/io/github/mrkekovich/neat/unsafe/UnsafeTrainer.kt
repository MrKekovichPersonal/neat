package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.TrainerConfiguration
import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.exceptions.OutOfSpeciesException
import io.github.mrkekovich.neat.interfaces.Trainer
import io.github.mrkekovich.neat.jni.TrainerJNI

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
    constructor(inputs: Long, outputs: Long) : this(TrainerJNI.create(inputs, outputs))

    constructor(configuration: TrainerConfiguration) : this(configuration.toTrainer())

    override val inputs: Long get() = ensureOpen { TrainerJNI.getInputs(pointer) }

    override val outputs: Long get() = ensureOpen { TrainerJNI.getOutputs(pointer) }

    override var maxIndividuals: Long
        get() = ensureOpen { TrainerJNI.getMaxIndividuals(pointer) }
        set(value) = ensureOpen { TrainerJNI.setMaxIndividuals(pointer, value) }

    override var deltaThreshold: Double
        get() = ensureOpen { TrainerJNI.getDeltaThreshold(pointer) }
        set(value) = ensureOpen { TrainerJNI.setDeltaThreshold(pointer, value) }

    override var disjointCoefficient: Double
        get() = ensureOpen { TrainerJNI.getC1(pointer) }
        set(value) = ensureOpen { TrainerJNI.setC1(pointer, value) }

    override var excessCoefficient: Double
        get() = ensureOpen { TrainerJNI.getC2(pointer) }
        set(value) = ensureOpen { TrainerJNI.setC2(pointer, value) }

    override var weightDifferenceCoefficient: Double
        get() = ensureOpen { TrainerJNI.getC3(pointer) }
        set(value) = ensureOpen { TrainerJNI.setC3(pointer, value) }

    override fun setCompatibilityFormula(
        disjointCoefficient: Double,
        excessCoefficient: Double,
        weightDifferenceCoefficient: Double,
    ): Unit = ensureOpen {
        TrainerJNI.setFormula(
            pointer,
            disjointCoefficient,
            excessCoefficient,
            weightDifferenceCoefficient
        )
    }

    override var weightChangeProbability: Double
        get() = ensureOpen { TrainerJNI.getChangeWeights(pointer) }
        set(value) = ensureOpen { TrainerJNI.setChangeWeights(pointer, value) }

    override var newNeuronProbability: Double
        get() = ensureOpen { TrainerJNI.getGuaranteedNewNeuron(pointer) }
        set(value) = ensureOpen { TrainerJNI.setGuaranteedNewNeuron(pointer, value) }

    override var maxLayers: Long
        get() = ensureOpen { TrainerJNI.getMaxLayers(pointer) }
        set(value) = ensureOpen { TrainerJNI.setMaxLayers(pointer, value) }

    override var maxPerLayers: Long
        get() = ensureOpen { TrainerJNI.getMaxPerLayers(pointer) }
        set(value) = ensureOpen { TrainerJNI.setMaxPerLayers(pointer, value) }

    override var enableCrossovers: Boolean
        get() = ensureOpen { TrainerJNI.getCrossovers(pointer) }
        set(value) = ensureOpen { TrainerJNI.setCrossovers(pointer, value) }

    override val maxSpeciesCount: Long get() = ensureOpen { TrainerJNI.getMaxSpeciesCount(pointer) }

    override fun getNewNetworks(): List<UnsafeNeuralNetwork> = ensureOpen {
        TrainerJNI.getNewNetworks(pointer).takeIf { it.isNotEmpty() }?.toNeuralNetworkList()
            ?: throw OutOfSpeciesException()
    }

    override fun getBestTopologies(): List<UnsafeTopology> = ensureOpen { TrainerJNI.getBestTopologies(pointer).toTopologyList() }

    /**
     * Adds a new species with the given unsafeTopology to the NEAT algorithm.
     *
     * @param unsafeTopology The UnsafeTopology object representing the initial unsafeTopology for the new species.
     */
    fun addSpecieWithTopology(unsafeTopology: UnsafeTopology): Unit = ensureOpen {
        TrainerJNI.addSpecieWithTopology(pointer, unsafeTopology.pointer)
    }


    override fun step(scores: DoubleArray): List<UnsafeNeuralNetwork> = ensureOpen {
        TrainerJNI.step(pointer, scores).takeIf { it.isNotEmpty() }?.toNeuralNetworkList()
            ?: throw OutOfSpeciesException()
    }

    override fun createNewSpecie(): Unit = ensureOpen { TrainerJNI.createNewSpecie(pointer) }

    @Deprecated("Might be deleted due to lack of use")
    fun finish(): Unit = ensureOpen { TrainerJNI.finish(pointer) }

    override fun destroy() {
        TrainerJNI.destroy(pointer)
        pointer = 0
    }
}

@OptIn(MemoryUnsafe::class)
private fun LongArray.toNeuralNetworkList(): List<UnsafeNeuralNetwork> = map { UnsafeNeuralNetwork(it) }

@OptIn(MemoryUnsafe::class)
private fun LongArray.toTopologyList(): List<UnsafeTopology> = map { UnsafeTopology(it) }

@OptIn(MemoryUnsafe::class)
private fun TrainerConfiguration.toTrainer(): Long = TrainerJNI.createWithAllParameters(
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