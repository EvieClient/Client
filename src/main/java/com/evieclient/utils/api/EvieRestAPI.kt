package com.evieclient.utils.api

import com.evieclient.Evie
import com.google.gson.Gson
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils


class EvieRestAPI {
    companion object {

        fun getPlayerCosmetics(name: String): PlayerCosmetics? {
            val gson = Gson()
            try {
                HttpClients.createDefault().use { client ->
                    val request =
                        HttpGet("http://localhost:3000/api/getPlayerCosmetics?name=$name")
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
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                ChatComponentText("§a§l[§f§lEvie§a§l] §fFailed to parse PlayerCosmetics for $name!")
                            )
                            println(e)
                            null
                        }
                        val playerCosmetics: PlayerCosmetics = gson.fromJson(body, PlayerCosmetics::class.java)
                        Evie.log("Current Cape for $name: ${playerCosmetics.activeCosmetics?.cape?.id}")
                        Evie.log("Failed to parse PlayerCosmetics for $name")
                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                            ChatComponentText("Current Cape for $name: ${playerCosmetics.activeCosmetics?.cape?.id}")
                        )
                        playerCosmetics
                    } else {
                        Evie.log("Error: ${response.statusLine.statusCode}")
                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                            ChatComponentText("§a§l[§f§lEvie§a§l] §fFailed to request PlayerCosmetics for $name! They Don't Exist!")
                        )
                        null
                    }
                }
            } catch (e: Exception) {
                Evie.log("Failed to request PlayerCosmetics for $name")
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                    ChatComponentText("§a§l[§f§lEvie§a§l] §fFailed to request PlayerCosmetics for $name! Most likely a parse error! | ${e.message}")
                )
                println(e)
                null
            }
            return null
        }
    }
}



