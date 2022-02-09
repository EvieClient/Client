package com.evieclient.utils.api

class PlayerCosmetics {
    // example response: {"activeCosmetics":{"cape":{"id":"SimpleEvieCape","type":"cape"}},"dev":false,"eviePlus":false,"media":false}
    var activeCosmetics: ActiveCosmetics? = null
    var dev: Boolean? = null
    var eviePlus: Boolean? = null
    var media: Boolean? = null
    var message: String? = null
}
