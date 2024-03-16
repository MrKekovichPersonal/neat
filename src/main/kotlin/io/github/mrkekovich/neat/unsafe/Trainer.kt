package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.TrainerConfiguration
import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.exceptions.OutOfSpeciesException
import io.github.mrkekovich.neat.jni.TrainerUtils

@MemoryUnsafe
@Suppress("unused")
class Trainer internal constructor(private var pointer: Long) : Destructible() {
    constructor(inputs: Long, outputs: Long) : this(TrainerUtils.create(inputs, outputs))

    constructor(configuration: TrainerConfiguration) : this(configuration.toTrainer())

    val inputs get() = ensureOpen { TrainerUtils.getInputs(pointer) }
    val outputs get() = ensureOpen { TrainerUtils.getOutputs(pointer) }

    var maxIndividuals
        get() = ensureOpen { TrainerUtils.getMaxIndividuals(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxIndividuals(pointer, value) }

    var deltaThreshold
        get() = ensureOpen { TrainerUtils.getDeltaThreshold(pointer) }
        set(value) = ensureOpen { TrainerUtils.setDeltaThreshold(pointer, value) }

    var disjointCoefficient
        get() = ensureOpen { TrainerUtils.getC1(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC1(pointer, value) }

    var excessCoefficient
        get() = ensureOpen { TrainerUtils.getC2(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC2(pointer, value) }

    var weightDifferenceCoefficient
        get() = ensureOpen { TrainerUtils.getC3(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC3(pointer, value) }

    fun setFormula(
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

    var weightChangeProbability: Double
        get() = ensureOpen { TrainerUtils.getChangeWeights(pointer) }
        set(value) = ensureOpen { TrainerUtils.setChangeWeights(pointer, value) }

    var newNeuronProbability: Double
        get() = ensureOpen { TrainerUtils.getGuaranteedNewNeuron(pointer) }
        set(value) = ensureOpen { TrainerUtils.setGuaranteedNewNeuron(pointer, value) }

    var maxLayers: Long
        get() = ensureOpen { TrainerUtils.getMaxLayers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxLayers(pointer, value) }

    var maxPerLayers: Long
        get() = ensureOpen { TrainerUtils.getMaxPerLayers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxPerLayers(pointer, value) }

    var enableCrossovers: Boolean
        get() = ensureOpen { TrainerUtils.getCrossovers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setCrossovers(pointer, value) }

    val maxSpeciesCount: Long
        get() = ensureOpen { TrainerUtils.getMaxSpeciesCount(pointer) }

    fun getNewNetworks(): List<NeuralNetwork> = ensureOpen {
        TrainerUtils.getNewNetworks(pointer).takeIf { it.isNotEmpty() }?.toNeuralNetworkList()
            ?: throw OutOfSpeciesException()
    }

    fun getBestTopologies(): List<Topology> = ensureOpen { TrainerUtils.getBestTopologies(pointer).toTopologyList() }

    fun addSpecieWithTopology(topology: Topology): Unit = ensureOpen {
        TrainerUtils.addSpecieWithTopology(pointer, topology.pointer)
    }

    fun step(results: DoubleArray): List<NeuralNetwork> = ensureOpen {
        TrainerUtils.step(pointer, results).takeIf { it.isNotEmpty() }?.toNeuralNetworkList()
            ?: throw OutOfSpeciesException()
    }

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

private fun TrainerConfiguration.toTrainer() = TrainerUtils.createWithAllParameters(
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