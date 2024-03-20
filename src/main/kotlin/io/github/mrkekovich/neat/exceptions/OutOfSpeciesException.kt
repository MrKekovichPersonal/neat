package io.github.mrkekovich.neat.exceptions

/**
 * Indicates that all species have been exhausted.
 */
class OutOfSpeciesException : RuntimeException("""
    All species have been exhausted. Help: Consider using `Trainer.createNewSpecie()` before training.
""".trimIndent())