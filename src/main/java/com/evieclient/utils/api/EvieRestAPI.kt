package com.evieclient.utils.api

import com.evieclient.Evie
import com.fasterxml.jackson.databind.ObjectMapper
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils


class EvieRestAPI {
    companion object {

        val objectMapper = ObjectMapper()

        fun getPlayerCosmetics(name: String): PlayerCosmetics? {
            try {
                HttpClients.createDefault().use { client ->
                    val request =
                        HttpGet("http://users.restapi.evie.pw/api/getPlayerCosmetics?name=$name")
                    Evie.log("Requesting PlayerCosmetics for $name")
                    val response: HttpResponse = client.execute(request)
                    return if (response.statusLine.statusCode == 200) {
                        Evie.log("Successfully retrieved PlayerCosmetics for $name")
                        val body = EntityUtils.toString(response.entity)
                        Evie.log("Body: $body")
                        try {
                            val playerCosmetics: PlayerCosmetics = objectMapper.readValue(body, PlayerCosmetics::class.java)
//                            Evie.log("Current Cape for $name: ${playerCosmetics.activeCosmetics?.cape?.id}")
//                            Evie.log("Failed to parse PlayerCosmetics for $name")
//                            Minecraft.getMinecraft().thePlayer.addChatMessage(
//                                ChatComponentText("Current Cape for $name: ${playerCosmetics.activeCosmetics?.cape?.id}")
//                            )
                            playerCosmetics
                        } catch (e: Exception) {
//                            Evie.log("Failed to parse PlayerCosmetics for $name")
//                            Minecraft.getMinecraft().thePlayer.addChatMessage(
//                                ChatComponentText("§a§l[§f§lEvie§a§l] §fFailed to parse PlayerCosmetics for $name! | §c§l${e.message}")
//                            )
//                            println(e)
                            null
                        }
                    } else {
//                        Evie.log("Error: ${response.statusLine.statusCode}")
//                        Minecraft.getMinecraft().thePlayer.addChatMessage(
//                            ChatComponentText("§a§l[§f§lEvie§a§l] §fFailed to request PlayerCosmetics for $name! They Don't Exist!")
//                        )
                        null
                    }
                }
            } catch (e: Exception) {
//                Evie.log("Failed to request PlayerCosmetics for $name")
//                Minecraft.getMinecraft().thePlayer.addChatMessage(
//                    ChatComponentText("§a§l[§f§lEvie§a§l] §fFailed to request PlayerCosmetics for $name! Most likely a parse error! | ${e.message}")
//                )
//                println(e)
                null
            }
            return null
        }
    }
}



