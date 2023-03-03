package com.seacroak.beefandblade.config;

import com.seacroak.beefandblade.BeefAndBlade;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class BNBModMenu implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return screen -> new Screen(Text.of("")) {
      @Override
      protected void init() {
        BNBConfigHandler.openConfigFile();
        try {
          this.client.setScreen(screen);
        } catch (NullPointerException e) {
          BeefAndBlade.LOGGER.error(e.getMessage());
        }
      }
    };
  }
}