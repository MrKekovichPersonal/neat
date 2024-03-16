package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.jni.TopologyUtils

@MemoryUnsafe
class UnsafeTopology internal constructor(internal var pointer: Long) : AutoCloseable {
    constructor(json: String) : this(TopologyUtils.loadJson(json))

    fun toJson() = TopologyUtils.toJson(pointer)

    override fun close() {
        TopologyUtils.destroy(pointer)
        pointer = 0
    }
}