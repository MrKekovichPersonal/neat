package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.interfaces.Topology
import io.github.mrkekovich.neat.jni.TopologyUtils

/**
 * UnsafeTopology is an unsafeNeuralNetwork class that represents the topology (structure) of a neural network.
 * It provides methods for loading and saving the topology from/to a JSON string, and destroying the underlying native object.
 * If not closed, will stay in memory until the JVM shuts down, so be careful with it.
 *
 * @property pointer A long value representing the pointer to the native object.
 */
@MemoryUnsafe
@Suppress("unused")
class UnsafeTopology internal constructor(internal var pointer: Long) : Topology, Destructible() {
    constructor(json: String) : this(TopologyUtils.loadJson(json))

    override fun toJson(): String = ensureOpen { TopologyUtils.toJson(pointer) }

    override fun destroy() {
        TopologyUtils.destroy(pointer)
        pointer = 0
    }
}