package io.github.mrkekovich.neat.exceptions

/**
 * Indicates that the native object is closed and deleted from the heap.
 */
class ClosedException(message: String? = null) : RuntimeException(message)