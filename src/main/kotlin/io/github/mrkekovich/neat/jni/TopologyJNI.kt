package io.github.mrkekovich.neat.jni

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.loadLib
/**
 * JNI bindings for native implementation of UnsafeTopology.
 */
@MemoryUnsafe
object TopologyJNI {
    @JvmStatic external fun loadJson(json: String): Long
    @JvmStatic external fun toJson(topology: Long): String
    @JvmStatic external fun destroy(topology: Long)
    init { loadLib() }
}