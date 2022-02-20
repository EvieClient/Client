package com.evieclient.utils.api

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.TimeUnit

class EviePlayers {
    companion object {
        // val players: MutableList<EviePlayer> = mutableListOf()
        val cache: Cache<String, EviePlayer> =
            Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(200).build()

        fun clear() {
            cache.invalidateAll()
        }

        private fun createPlayer(name: String) {
            cache.put(name, EviePlayer(name))
        }

        fun playerExists(name: String): Boolean {
            return if (cache.getIfPresent(name) != null) {
                true
            } else {
                createPlayer(name)
                true
            }
        }

        // need to convert bottom 2 to the cache system

        fun playerHasCape(name: String): Boolean {
            return if (cache.getIfPresent(name) != null) {
                cache.getIfPresent(name)!!.cosmetics?.activeCosmetics?.cape != null
            } else {
                createPlayer(name)
                cache.getIfPresent(name)?.cosmetics?.activeCosmetics?.cape != null
            }
        }

        fun getPlayer(name: String): EviePlayer? {
            return if (cache.getIfPresent(name) != null) {
                cache.getIfPresent(name)
            } else {
                createPlayer(name)
                cache.getIfPresent(name)
            }
        }
    }
}