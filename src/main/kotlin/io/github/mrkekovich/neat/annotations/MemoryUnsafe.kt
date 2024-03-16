package io.github.mrkekovich.neat.annotations

/**
 * Indicates that following code is unsafeNeuralNetwork due to its use of native methods.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = """
        This code is unsafeNeuralNetwork due to its use of native methods. Requires to be closed ONCE.
        Otherwise, it can lead to severe memory leaks and crashes.
    """
)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
)
@Retention(AnnotationRetention.BINARY)
annotation class MemoryUnsafe
