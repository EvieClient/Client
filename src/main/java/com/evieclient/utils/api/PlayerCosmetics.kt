package com.evieclient.utils.api

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import javax.annotation.Generated


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(*["activeCosmetics", "dev", "eviePlus", "media"])
@Generated("jsonschema2pojo")
class PlayerCosmetics {
    @get:JsonProperty("activeCosmetics")
    @set:JsonProperty("activeCosmetics")
    @JsonProperty("activeCosmetics")
    var activeCosmetics: ActiveCosmetics? = null

    @get:JsonProperty("dev")
    @set:JsonProperty("dev")
    @JsonProperty("dev")
    var dev: Boolean? = null

    @get:JsonProperty("eviePlus")
    @set:JsonProperty("eviePlus")
    @JsonProperty("eviePlus")
    var eviePlus: Boolean? = null

    @get:JsonProperty("media")
    @set:JsonProperty("media")
    @JsonProperty("media")
    var media: Boolean? = null

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