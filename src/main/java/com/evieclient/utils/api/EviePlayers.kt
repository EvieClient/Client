package com.evieclient.utils.api

class EviePlayers {
    companion object {
        val players: HashMap<String, PlayerCosmetics?> = HashMap<String, PlayerCosmetics?>()

         fun addPlayer(uuid: String, playerCosmetics: PlayerCosmetics?) {
            players.put(uuid, playerCosmetics);
        }

        fun playerExists(uuid: String): Boolean {
            return players.containsKey(uuid)
        }

        fun getPlayer(uuid: String): PlayerCosmetics? {
            return players.get(uuid);
        }

    }
}