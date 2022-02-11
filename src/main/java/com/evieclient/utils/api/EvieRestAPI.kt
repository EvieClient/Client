package com.evieclient.utils.api

import com.evieclient.Evie
import com.fasterxml.jackson.databind.ObjectMapper
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients


class EvieRestAPI {
    companion object {

        fun reqEviePlayer(uuid: String) {
            val mapper = ObjectMapper()

            Evie.log("Requesting PlayerCosmetics for $uuid")

            HttpClients.createDefault().use { client ->
                val request =
                    HttpGet("https://evie.pw/api/getPlayerCosmetics?uuid=$uuid")
                val playerCosmetics: PlayerCosmetics = client.execute<PlayerCosmetics>(
                    request
                ) { httpResponse: HttpResponse ->
                    mapper.readValue(
                        httpResponse.entity.content,
                        PlayerCosmetics::class.java
                    )
                }

                EviePlayers.addPlayer(uuid, playerCosmetics)
                Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1.0f, 1.0f)

                Evie.log("Player's Current Cape: ${playerCosmetics.activeCosmetics?.cape}")
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("${uuid} - ${playerCosmetics.activeCosmetics?.cape}"))

            }
        }
    }
}