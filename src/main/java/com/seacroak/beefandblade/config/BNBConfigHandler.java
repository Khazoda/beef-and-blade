package com.seacroak.beefandblade.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.seacroak.beefandblade.BeefAndBlade;
import net.minecraft.util.Util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BNBConfigHandler {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static BNBConfig readConfig() throws IOException {
    String configPath = System.getProperty("user.dir") + "/config/" + BeefAndBlade.MOD_ID + ".json";
    BeefAndBlade.LOGGER.debug("Config path: " + configPath);

    // Read config
    BNBConfig config;
    try (FileReader reader = new FileReader(configPath)) {
      config = GSON.fromJson(reader, BNBConfig.class);
      if (config == null) {
        throw new NullPointerException();
      }
      config.validate();
      try (FileWriter writer = new FileWriter(configPath)) {
        GSON.toJson(config, writer);
      }

      BeefAndBlade.LOGGER.debug("Config: " + config);
    } catch (NullPointerException | JsonParseException | IOException e) {
      // Create new config
      config = new BNBConfig();
      File file = new File(configPath);
      file.getParentFile().mkdirs();
      try (FileWriter writer = new FileWriter(configPath)) {
        GSON.toJson(config, writer);
        BeefAndBlade.LOGGER.debug("New config file created");
      }
    }
    return config;
  }

  public static void openConfigFile() {
    String configPath = System.getProperty("user.dir") + "/config/" + BeefAndBlade.MOD_ID + ".json";

    Util.getOperatingSystem().open(new File(configPath));
  }
}
