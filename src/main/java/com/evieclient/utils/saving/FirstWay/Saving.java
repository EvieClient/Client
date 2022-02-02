package com.evieclient.utils.saving.FirstWay;

import java.io.File;

public class Saving {
  public Saving() {
    Saving.saveIsEnabledToFile("ExampleMod", false);
  }

  /**
   * Saving booleans to a json
   *
   * @author Pinkulu | pinkulu#6260
   * @since v1.0.0 this will make a folder for each mod, and add a toggle.json in each folder (not
   *     sure if it will work as this is taken from my old client and will prob need some
   *     modifiying, cant test it out rn bc there arnt mods yet)
   *     <p>run this whenever a change is made *
   */
  public static File getFolder(String mod) {
    File file = new File(FileManager.getModsDirectory(), mod);
    file.mkdirs();
    return file;
  }

  public static void saveIsEnabledToFile(String mod, Boolean b) {
    FileManager.writeJsonToFile(new File(getFolder(mod), "toggle.json"), b);
  }

  public static Boolean loadEnabledFromFile(String mod) {
    Boolean b = FileManager.readFromJson(new File(getFolder(mod), "toggle.json"), Boolean.class);
    if (b == null) {
      b = false;
      saveIsEnabledToFile(mod, b);
    }
    return b;
  }
  /**
   * @example is the manual/mod class do: public void setEnabled(boolean isEnabled) {
   *     super.setEnabled(Saving.loadEnabledFromFile("ExampleMod"));
   *     Saving.saveIsEnabledToFile("ExampleMod", isEnabled); }
   */
}
