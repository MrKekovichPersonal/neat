package io.github.mrkekovich.neat.jni

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.loadLib

/**
 * JNI bindings for native implementation of trainer.
 */
@MemoryUnsafe
internal object TrainerJNI {
    @JvmStatic external fun create(input: Long, output: Long): Long
    @JvmStatic external fun createWithAllParameters(
        inputs: Long,
        outputs: Long,
        maxIndividuals: Long = 100,
        maxLayers: Long = 4,
        maxPerLayer: Long = 20,
        deltaThreshold: Double = 3.0,
        disjointCoefficient: Double = 1.0,
        excessCoefficient: Double = 1.0,
        weightDifferenceCoefficient: Double = 1.0,
        enableCrossovers: Boolean = true,
        weightChangeProbability: Double = 0.95,
        newNeuronProbability: Double = 0.2,
    ): Long
    @JvmStatic external fun getInputs(trainer: Long): Long
    @JvmStatic external fun getOutputs(trainer: Long): Long
    @JvmStatic external fun getMaxIndividuals(trainer: Long): Long
    @JvmStatic external fun setMaxIndividuals(trainer: Long, maxIndividuals: Long)
    @JvmStatic external fun getDeltaThreshold(trainer: Long): Double
    @JvmStatic external fun setDeltaThreshold(trainer: Long, deltaThreshold: Double)
    @JvmStatic external fun getC1(trainer: Long): Double
    @JvmStatic external fun setC1(trainer: Long, c1: Double)
    @JvmStatic external fun getC2(trainer: Long): Double
    @JvmStatic external fun setC2(trainer: Long, c2: Double)
    @JvmStatic external fun getC3(trainer: Long): Double
    @JvmStatic external fun setC3(trainer: Long, c3: Double)
    @JvmStatic external fun setFormula(trainer: Long, c1: Double, c2: Double, c3: Double)
    @JvmStatic external fun getChangeWeights(trainer: Long): Double
    @JvmStatic external fun setChangeWeights(trainer: Long, changeWeights: Double)
    @JvmStatic external fun getGuaranteedNewNeuron(trainer: Long): Double
    @JvmStatic external fun setGuaranteedNewNeuron(trainer: Long, guaranteedNewNeuron: Double)
    @JvmStatic external fun getMaxLayers(trainer: Long): Long
    @JvmStatic external fun setMaxLayers(trainer: Long, maxLayers: Long)
    @JvmStatic external fun getMaxPerLayers(trainer: Long): Long
    @JvmStatic external fun setMaxPerLayers(trainer: Long, maxPerLayers: Long)
    @JvmStatic external fun getCrossovers(trainer: Long): Boolean
    @JvmStatic external fun setCrossovers(trainer: Long, crossovers: Boolean)
    @JvmStatic external fun getMaxSpeciesCount(trainer: Long): Long
    @JvmStatic external fun getNewNetworks(trainer: Long): LongArray
    @JvmStatic external fun createNewSpecie(trainer: Long)
    @JvmStatic external fun destroy(trainer: Long)
    @JvmStatic external fun step(trainer: Long, results: DoubleArray): LongArray
    @JvmStatic external fun finish(trainer: Long)
    @JvmStatic external fun addSpecieWithTopology(trainer: Long, topology: Long)
    @JvmStatic external fun getBestTopologies(trainer: Long): LongArray
    init { loadLib() }
}
