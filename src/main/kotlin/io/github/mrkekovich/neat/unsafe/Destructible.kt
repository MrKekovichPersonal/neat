package io.github.mrkekovich.neat.unsafe

import io.github.mrkekovich.neat.exceptions.ClosedException

/**
 * An object that can be destructed.
 */
abstract class Destructible : AutoCloseable {
    @Volatile
    var isOpen = true
        protected set

    protected inline fun <R> ensureOpen(block: () -> R): R =
        if (isOpen) block()
        else throw ClosedException("Destructible was closed.")

    /**
     * Destroys the underlying native object associated with a class.
     */
    protected abstract fun destroy()


    final override fun close() {
        isOpen = false
        destroy()
    }
}