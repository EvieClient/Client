package com.evieclient.utils.api

class EviePlayers {
    companion object {
        val players: MutableList<EviePlayer> = mutableListOf()

        fun clear() {
            players.clear()
        }

        private fun createPlayer(name: String) {
            players.add(EviePlayer(name))
        }

        fun playerExists(name: String): Boolean {
            return if (players.any { it.name == name }) {
                true
            } else {
                createPlayer(name)
                true
            }
        }

        fun getPlayer(name: String): EviePlayer? {
            return players.find { it.name == name }
        }
    }
}