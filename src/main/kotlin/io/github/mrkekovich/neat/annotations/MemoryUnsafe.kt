package io.github.mrkekovich.neat.annotations

@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This class is unsafe and requires to be closed. Otherwise, it can lead to memory leaks."
)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class MemoryUnsafe()
