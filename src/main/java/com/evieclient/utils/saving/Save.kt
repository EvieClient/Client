package com.evieclient.utils.saving

import com.evieclient.Evie
import com.evieclient.modules.Module
import com.evieclient.modules.hud.RenderModule
import java.io.FileWriter
import com.google.gson.JsonParser
import com.google.gson.stream.JsonWriter
import io.sentry.Sentry
import java.io.File
import java.io.FileReader
import java.io.IOException

object Save {

    fun saveConfig() {
        try {
            val file = File(Evie.evieDir, "toggle.json")
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
            }
            val writer = JsonWriter(FileWriter(file, false))
            writeJson(writer)
            writer.close()
        } catch (e: Throwable) {
            Sentry.captureException(e)
            e.printStackTrace()
        }
    }

    fun loadConfig() {
        try {
            val file = File(Evie.evieDir, "toggle.json")
            if (file.exists()) {
                val reader = JsonParser().parse(FileReader(file))
                val jsonObject = reader.asJsonObject
                jsonObject.getAsJsonObject("modules").entrySet().forEach {
                    val jsonElement = it.key
                    val module = Evie.MODULE_MANAGER.modules.find { it.name == jsonElement }
                    if (module != null) {
                        module.enabled = it.value.asBoolean
                    }
                }
                jsonObject.getAsJsonObject("render").entrySet().forEach {
                    val jsonElement = it.key
                    val module = Evie.MODULE_MANAGER.renderModuleList.find { it.name == jsonElement }
                    if (module != null) {
                        module.enabled = it.value.asJsonObject.get("enabled").asBoolean
                        module.x = it.value.asJsonObject.get("x").asInt
                        module.y = it.value.asJsonObject.get("y").asInt
                    }
                }
            }
        } catch (e: Throwable) {
            Sentry.captureException(e)
            e.printStackTrace()
        }
        return;
    }

    @Throws(IOException::class)
    fun writeJson(writer: JsonWriter) {
        val modules: MutableList<Module> = mutableListOf()
        val renderModules: MutableList<RenderModule> = mutableListOf()

        writer.beginObject()
        writer.name("modules")
        writer.beginObject()
        for (module in Evie.MODULE_MANAGER.moduleList) {
            if(modules.contains(module)) continue
            writer.name(module.name)
            writer.value(module.enabled)
            modules.add(module)
        }
        writer.endObject()

        writer.name("render")
        writer.beginObject()
        for (module in Evie.MODULE_MANAGER.renderModuleList) {
            if(renderModules.contains(module)) continue

            writer.name(module.name)
            writer.jsonValue("{\"enabled\": ${module.enabled}, \"x\": ${module.x}, \"y\": ${module.y} }")
            renderModules.add(module)
        }
        writer.endObject()
        writer.endObject()
    }
}