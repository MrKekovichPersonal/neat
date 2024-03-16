package io.github.mrkekovich.neat.jni

import io.github.mrkekovich.neat.loadLib

internal object TopologyUtils {
    external fun loadJson(json: String): Long
    external fun toJson(topology: Long): String
    external fun destroy(topology: Long)
    init { loadLib() }
}