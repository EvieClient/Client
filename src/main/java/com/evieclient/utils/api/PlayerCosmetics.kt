package com.evieclient.utils.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated


@Generated("jsonschema2pojo")
class PlayerCosmetics {
    @SerializedName("activeCosmetics")
    @Expose
    var activeCosmetics: ActiveCosmetics? = null

    @SerializedName("dev")
    @Expose
    var dev: Boolean? = null

    @SerializedName("eviePlus")
    @Expose
    var eviePlus: Boolean? = null

    @SerializedName("media")
    @Expose
    var media: Boolean? = null
}