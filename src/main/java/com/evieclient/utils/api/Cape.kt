package com.evieclient.utils.api

import com.fasterxml.jackson.annotation.*
import net.minecraft.util.ResourceLocation
import java.util.*
import javax.annotation.Generated


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id")
@Generated("jsonschema2pojo")
class Cape {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: String? = null

    @JsonIgnore
    private val additionalProperties: MutableMap<String, Any> = HashMap()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        additionalProperties[name] = value
    }

    var dynamicCapeTexture: ResourceLocation? = null

}