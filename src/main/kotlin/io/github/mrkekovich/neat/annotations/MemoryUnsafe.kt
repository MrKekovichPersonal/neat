package io.github.mrkekovich.neat.annotations

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This class is unsafe and requires to be closed. Otherwise, it can lead to severe memory leaks."
)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class MemoryUnsafe
