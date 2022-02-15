package com.evieclient.utils.api

import com.fasterxml.jackson.annotation.*
import javax.annotation.Generated


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("cape")
@Generated("jsonschema2pojo")
class ActiveCosmetics {
    @get:JsonProperty("cape")
    @set:JsonProperty("cape")
    @JsonProperty("cape")
    var cape: Cape? = null

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
}