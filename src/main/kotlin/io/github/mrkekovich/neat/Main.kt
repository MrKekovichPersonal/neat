package io.github.mrkekovich.neat

fun main() {
    val trainer = Trainer(2, 2)
    println(trainer.c1)
    trainer.getNewNetworks().forEach {
        it.compute(doubleArrayOf(0.0, 0.0))
        it.close()
    }
}