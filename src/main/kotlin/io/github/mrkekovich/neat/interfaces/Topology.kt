package io.github.mrkekovich.neat.interfaces

interface Topology {
    /**
     * Converts the topology to a JSON string.
     *
     * @return A String containing the JSON representation of the topology.
     */
    fun toJson(): String
}
