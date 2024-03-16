package io.github.mrkekovich.neat.wrappers

import io.github.mrkekovich.neat.TrainerConfiguration
import io.github.mrkekovich.neat.annotations.MemoryUnsafe
import io.github.mrkekovich.neat.interfaces.Trainer
import io.github.mrkekovich.neat.unsafe.UnsafeTrainer

@OptIn(MemoryUnsafe::class)
@Suppress("unused")
class TrainerWrapper(val unsafeTrainer: UnsafeTrainer) {
    constructor(configuration: TrainerConfiguration) : this(UnsafeTrainer(configuration))

    inline fun <R> use(block: (Trainer) -> R): R = unsafeTrainer.use(block)
}