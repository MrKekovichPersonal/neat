package io.github.mrkekovich.neat

import io.github.mrkekovich.neat.NeuralNetwork.Companion.toNeuralNetworks
import io.github.mrkekovich.neat.jni.TrainerUtils

class Trainer internal constructor(private var pointer: Long) : AutoCloseable {
    init {
        TrainerUtils.init(pointer)
    }

    constructor(inputs: Long, outputs: Long) : this(TrainerUtils.create(inputs, outputs))

    constructor(
        inputs: Long,
        outputs: Long,
        maxIndividuals: Long = 100,
        maxLayers: Long = 4,
        maxPerLayers: Long = 20,
        deltaThreshold: Double = 3.0,
        c1: Double = 1.0,
        c2: Double = 1.0,
        c3: Double = 1.0,
        crossovers: Boolean = true,
        changeWeights: Double = 0.95,
        guaranteedNewNeuron: Double = 0.2,
    ) : this(
        TrainerUtils.createWithAllParameters(
            inputs = inputs,
            outputs = outputs,
            maxIndividuals = maxIndividuals,
            maxLayers = maxLayers,
            maxPerLayers = maxPerLayers,
            deltaThreshold = deltaThreshold,
            c1 = c1,
            c2 = c2,
            c3 = c3,
            crossovers = crossovers,
            changeWeights = changeWeights,
            guaranteedNewNeuron = guaranteedNewNeuron,
        )
    )

    constructor(configuration: TrainerConfiguration) : this(
        TrainerUtils.createWithAllParameters(
            inputs = configuration.inputs,
            outputs = configuration.outputs,
            maxIndividuals = configuration.maxIndividuals,
            maxLayers = configuration.maxLayers,
            maxPerLayers = configuration.maxPerLayers,
            deltaThreshold = configuration.deltaThreshold,
            c1 = configuration.c1,
            c2 = configuration.c2,
            c3 = configuration.c3,
            crossovers = configuration.crossovers,
            changeWeights = configuration.changeWeights,
            guaranteedNewNeuron = configuration.guaranteedNewNeuron,
        )
    )

    val inputs get() = ensureOpen { TrainerUtils.getInputs(pointer) }
    val outputs get() = ensureOpen { TrainerUtils.getOutputs(pointer) }

    var maxIndividuals
        get() = ensureOpen { TrainerUtils.getMaxIndividuals(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxIndividuals(pointer, value) }

    var deltaThreshold
        get() = ensureOpen { TrainerUtils.getDeltaThreshold(pointer) }
        set(value) = ensureOpen { TrainerUtils.setDeltaThreshold(pointer, value) }

    var c1
        get() = ensureOpen { TrainerUtils.getC1(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC1(pointer, value) }

    var c2
        get() = ensureOpen { TrainerUtils.getC2(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC2(pointer, value) }

    var c3
        get() = ensureOpen { TrainerUtils.getC3(pointer) }
        set(value) = ensureOpen { TrainerUtils.setC3(pointer, value) }

    fun setFormula(c1: Double, c2: Double, c3: Double) = ensureOpen { TrainerUtils.setFormula(pointer, c1, c2, c3) }

    var changeWeights
        get() = ensureOpen { TrainerUtils.getChangeWeights(pointer) }
        set(value) = ensureOpen { TrainerUtils.setChangeWeights(pointer, value) }

    var guaranteedNewNeuron
        get() = ensureOpen { TrainerUtils.getGuaranteedNewNeuron(pointer) }
        set(value) = ensureOpen { TrainerUtils.setGuaranteedNewNeuron(pointer, value) }

    var maxLayers
        get() = ensureOpen { TrainerUtils.getMaxLayers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxLayers(pointer, value) }

    var maxPerLayers
        get() = ensureOpen { TrainerUtils.getMaxPerLayers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setMaxPerLayers(pointer, value) }

    var crossovers
        get() = ensureOpen { TrainerUtils.getCrossovers(pointer) }
        set(value) = ensureOpen { TrainerUtils.setCrossovers(pointer, value) }

    val maxSpeciesCount
        get() = ensureOpen { TrainerUtils.getMaxSpeciesCount(pointer) }

    fun getNewNetworks() = ensureOpen { TrainerUtils.getNewNetworks(pointer).toNeuralNetworks() }

    fun step(results: DoubleArray) = ensureOpen { TrainerUtils.step(pointer, results).toNeuralNetworks() }

    fun finish() = ensureOpen { TrainerUtils.finish(pointer) }

    override fun close() {
        isOpen = false
        TrainerUtils.destroy(pointer)
        pointer = 0
    }

    var isOpen = true
        private set

    private inline fun <R> ensureOpen(block: () -> R): R =
        if (isOpen) block()
        else throw IllegalStateException("Trainer was closed.")
}