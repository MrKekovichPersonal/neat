package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.exceptions.ClosedException

abstract class Destructible : AutoCloseable {
    @Volatile
    var isOpen = true
        protected set

    protected inline fun <R> ensureOpen(block: () -> R): R =
        if (isOpen) block()
        else throw ClosedException("Destructible was closed.")

    protected abstract fun destroy()

    final override fun close() {
        isOpen = false
        destroy()
    }
}