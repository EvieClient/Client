package com.evieclient.utils.saving.OtherWay;

import com.evieclient.Evie;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import io.sentry.Sentry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Saving booleans to a json (and other things prob)
 *
 * @author Pinkulu | pinkulu#6260 *
 */
public class Save {

  /** run this whenever a change is made * */
  public static void saveConfig() {
    try {
      File file = new File(Evie.evieDir, "toggle.json");
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }
      JsonWriter writer = new JsonWriter(new FileWriter(file, false));
      writeJson(writer);
      writer.close();
    } catch (Throwable e) {
  Sentry.captureException(e);
      e.printStackTrace();
    }
  }

  /** run this when the game is Initializing * */
  public static void loadConfig() {
    try {
      File file = new File(Evie.evieDir, "toggle.json");
      if (file.exists()) readJson(file);
    } catch (Throwable e) {
  Sentry.captureException(e);
      e.printStackTrace();
    }
  }

  /** add the booleans from example * */
  public static void readJson(File file) throws Throwable {
    JsonParser parser = new JsonParser();
    JsonObject json = parser.parse(new FileReader(file)).getAsJsonObject();
    json = json.getAsJsonObject("Toggle");
  }

  /** add the booleans from example * */
  public static void writeJson(JsonWriter writer) throws IOException {
    writer.setIndent(" ");
    writer.beginObject();
    writer.name("First thing");
    writer.beginObject();
    writer.endObject();
    writer.endObject();
  }
}
