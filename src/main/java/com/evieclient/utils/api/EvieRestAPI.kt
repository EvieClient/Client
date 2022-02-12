package com.evieclient.utils.api

import com.evieclient.Evie
import com.google.gson.Gson
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils


class EvieRestAPI {
    companion object {

        fun getPlayerCosmetics(name: String): PlayerCosmetics? {
            val gson = Gson()

            HttpClients.createDefault().use { client ->
                val request =
                    HttpGet("https://evie.pw/api/getPlayerCosmetics?name=$name")
                Evie.log("Requesting PlayerCosmetics for $name")
                val response: HttpResponse = client.execute(request)
                return if (response.statusLine.statusCode == 200) {
                    Evie.log("Successfully retrieved PlayerCosmetics for $name")
                    val body = EntityUtils.toString(response.entity)
                    Evie.log("Body: $body")
                    try {
                        gson.fromJson(body, PlayerCosmetics::class.java)
                    } catch (e: Exception) {
                        Evie.log("Failed to parse PlayerCosmetics for $name")
                        println(e)
                        null
                    }
                    val playerCosmetics: PlayerCosmetics = gson.fromJson(body, PlayerCosmetics::class.java)
                    Evie.log("Current Cape for $name: ${playerCosmetics.activeCosmetics?.cape?.id}")
                    playerCosmetics
                } else {
                    Evie.log("Error: ${response.statusLine.statusCode}")
                    null
                }
            }
        }
    }
}

