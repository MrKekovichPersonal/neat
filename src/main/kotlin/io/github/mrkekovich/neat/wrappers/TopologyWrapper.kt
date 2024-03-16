package io.github.mrkekovich.neat.wrappers

import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.interfaces.Topology
import io.github.mrkekovich.neat.unsafe.UnsafeTopology
import java.io.File

@OptIn(MemoryUnsafe::class)
@Suppress("unused")
class TopologyWrapper(val unsafeTopology: UnsafeTopology) {
    constructor(file: File) : this(
        file.takeIf { it.extension == "json" }
            ?.let { UnsafeTopology(it.readText()) }
            ?: throw IllegalArgumentException("File must have .json extension")
    )

    inline fun <R> use(block: (Topology) -> R): R = unsafeTopology.use(block)
}