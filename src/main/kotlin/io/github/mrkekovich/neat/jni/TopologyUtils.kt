package io.github.mrkekovich.neat.jni

import io.github.mrkekovich.neat.loadLib
/**
 * JNI bindings for native implementation of Topology.
 */
internal object TopologyUtils {
    external fun loadJson(json: String): Long
    external fun toJson(topology: Long): String
    external fun destroy(topology: Long)
    init { loadLib() }
}