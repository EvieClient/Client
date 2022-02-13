package com.evieclient.utils.api

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.ResourceLocation

@Generated("jsonschema2pojo")
class Cape {
    @SerializedName("id")
    @Expose
    var id: String? = null

    var dynamicCapeTexture: ResourceLocation? = null
}