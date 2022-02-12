package com.evieclient.utils.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import net.minecraft.util.ResourceLocation
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class Cape {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    var dynamicCapeTexture: ResourceLocation? = null
}
