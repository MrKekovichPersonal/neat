package io.github.mrkekovich.neat

data class TrainerConfiguration(
    val inputs: Long,
    val outputs: Long,
    val maxIndividuals: Long = 100,
    val maxLayers: Long = 4,
    val maxPerLayers: Long = 20,
    val deltaThreshold: Double = 3.0,
    val c1: Double = 1.0,
    val c2: Double = 1.0,
    val c3: Double = 1.0,
    val crossovers: Boolean = true,
    val changeWeights: Double = 0.95,
    val guaranteedNewNeuron: Double = 0.2,
)
