package io.github.mrkekovich.neat.jni

import io.github.mrkekovich.neat.loadLib

object TrainerUtils {
    external fun create(input: Long, output: Long): Long

    external fun createWithAllParameters(
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
    ): Long

    external fun getInputs(trainer: Long): Long
    external fun getOutputs(trainer: Long): Long

    external fun getMaxIndividuals(trainer: Long): Long
    external fun setMaxIndividuals(trainer: Long, maxIndividuals: Long)

    external fun getDeltaThreshold(trainer: Long): Double
    external fun setDeltaThreshold(trainer: Long, deltaThreshold: Double)

    external fun getC1(trainer: Long): Double
    external fun setC1(trainer: Long, c1: Double)

    external fun getC2(trainer: Long): Double
    external fun setC2(trainer: Long, c2: Double)

    external fun getC3(trainer: Long): Double
    external fun setC3(trainer: Long, c3: Double)

    external fun setFormula(trainer: Long, c1: Double, c2: Double, c3: Double)

    external fun getChangeWeights(trainer: Long): Double
    external fun setChangeWeights(trainer: Long, changeWeights: Double)

    external fun getGuaranteedNewNeuron(trainer: Long): Double
    external fun setGuaranteedNewNeuron(trainer: Long, guaranteedNewNeuron: Double)

    external fun getMaxLayers(trainer: Long): Long
    external fun setMaxLayers(trainer: Long, maxLayers: Long)

    external fun getMaxPerLayers(trainer: Long): Long
    external fun setMaxPerLayers(trainer: Long, maxPerLayers: Long)

    external fun getCrossovers(trainer: Long): Boolean
    external fun setCrossovers(trainer: Long, crossovers: Boolean)

    external fun getMaxSpeciesCount(trainer: Long): Long

    external fun getNewNetworks(trainer: Long): LongArray
    external fun init(trainer: Long)
    external fun destroy(trainer: Long)
    external fun step(trainer: Long, results: DoubleArray): LongArray
    external fun finish(trainer: Long)

    init { loadLib() }
}
