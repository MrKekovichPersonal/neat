package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.jni.TopologyUtils

@MemoryUnsafe
@Suppress("unused")
class Topology internal constructor(internal var pointer: Long) : Destructible() {
    constructor(json: String) : this(TopologyUtils.loadJson(json))

    fun toJson() = TopologyUtils.toJson(pointer)

    override fun destroy() {
        TopologyUtils.destroy(pointer)
        pointer = 0
    }
}