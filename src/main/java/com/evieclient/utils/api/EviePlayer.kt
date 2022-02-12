package com.evieclient.utils.api

import com.evieclient.utils.misc.ThreadManager

class EviePlayer(name: String) {
    var cosmetics: PlayerCosmetics? = null
    val name: String = name

    init {
        ThreadManager.runAsync(Runnable {
            var cosmetics: PlayerCosmetics? = EvieRestAPI.getPlayerCosmetics(name)
            this.cosmetics = cosmetics
        })
    }

}
