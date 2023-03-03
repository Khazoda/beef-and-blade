package com.seacroak.beefandblade;

import com.seacroak.beefandblade.config.BNBConfig;
import com.seacroak.beefandblade.config.BNBConfigHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BeefAndBlade implements ModInitializer {
  // This logger is used to write text to the console and the log file.
  // It is considered best practice to use your mod id as the logger's name.
  // That way, it's clear which mod wrote info, warnings, and errors.
  public static final Logger LOGGER = LoggerFactory.getLogger("beef-and-blade");
  public static final String MOD_ID = "beef-and-blade";
  public static final String MOD_NAME = "Beef & Blade";


  private static final Identifier COW_LOOT_TABLE_ID = EntityType.COW.getLootTableId();
  public static BNBConfig config;
  public static boolean initConfig = false;

  @Override
  public void onInitialize() {
    // Config Initialization
    if (!initConfig) {
      try {
        config = BNBConfigHandler.readConfig();
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
      LOGGER.info(MOD_NAME + " config initialized");
      initConfig = true;
    }

    // Data Defaults
//    int sword_leather_min = 2;
//    int sword_leather_max = 3;
//
//    int sword_beef_min = 0;
//    int sword_beef_max = 0;
//
//    int axe_leather_min = 0;
//    int axe_leather_max = 0;
//
//    int axe_beef_min = 4;
//    int axe_beef_max = 5;

    // Loot Table Instantiation
    LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
      if (source.isBuiltin() && COW_LOOT_TABLE_ID.equals(id)) {
        if (source != LootTableSource.VANILLA) {
          throw new AssertionError("[Warning] Should reference vanilla cow loot table");
        }
        // DEFAULT BEEF DROPS VALUE
        LootPool.Builder beefDefaultBuilder = LootPool.builder()
          .conditionally(
            InvertedLootCondition.builder(EntityPropertiesLootCondition
              .builder(LootContext.EntityTarget.KILLER,
                new EntityPredicate.Builder()
                  .equipment(EntityEquipmentPredicate.Builder.create()
                    .mainhand(ItemPredicate.Builder.create()
                      .items(Items.WOODEN_AXE, Items.STONE_AXE,
                        Items.IRON_AXE, Items.GOLDEN_AXE,
                        Items.DIAMOND_AXE, Items.NETHERITE_AXE,
                        Items.WOODEN_SWORD, Items.STONE_SWORD,
                        Items.IRON_SWORD, Items.GOLDEN_SWORD,
                        Items.DIAMOND_SWORD,
                        Items.NETHERITE_SWORD)
                      .build())
                    .build())
                  .build())))
          .with(ItemEntry.builder(Items.BEEF))
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 3.0f)).build());

        // DEFAULT LEATHER DROPS VALUE
        LootPool.Builder leatherDefaultBuilder = LootPool.builder()
          .conditionally(
            InvertedLootCondition.builder(EntityPropertiesLootCondition
              .builder(LootContext.EntityTarget.KILLER,
                new EntityPredicate.Builder()
                  .equipment(EntityEquipmentPredicate.Builder.create()
                    .mainhand(ItemPredicate.Builder.create()
                      .items(Items.WOODEN_AXE, Items.STONE_AXE,
                        Items.IRON_AXE, Items.GOLDEN_AXE,
                        Items.DIAMOND_AXE, Items.NETHERITE_AXE,
                        Items.WOODEN_SWORD, Items.STONE_SWORD,
                        Items.IRON_SWORD, Items.GOLDEN_SWORD,
                        Items.DIAMOND_SWORD,
                        Items.NETHERITE_SWORD)
                      .build())
                    .build())
                  .build())))
          .with(ItemEntry.builder(Items.LEATHER))
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)).build());

        // COW KILLED WITH AXE - BEEF
        LootPool.Builder beefAxeBuilder = LootPool.builder()
          .conditionally(
            EntityPropertiesLootCondition
              .builder(LootContext.EntityTarget.KILLER,
                new EntityPredicate.Builder()
                  .equipment(EntityEquipmentPredicate.Builder.create()
                    .mainhand(ItemPredicate.Builder.create()
                      .items(Items.WOODEN_AXE, Items.STONE_AXE,
                        Items.IRON_AXE, Items.GOLDEN_AXE,
                        Items.DIAMOND_AXE, Items.NETHERITE_AXE)
                      .build())
                    .build())
                  .build()))
          .with(ItemEntry.builder(Items.BEEF))
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(config.axe_beef_range.getMin(),config.axe_beef_range.getMax())).build());

        // COW KILLED WITH AXE - LEATHER
        LootPool.Builder leatherAxeBuilder = LootPool.builder()
          .conditionally(
            EntityPropertiesLootCondition
              .builder(LootContext.EntityTarget.KILLER,
                new EntityPredicate.Builder()
                  .equipment(EntityEquipmentPredicate.Builder.create()
                    .mainhand(ItemPredicate.Builder.create()
                      .items(Items.WOODEN_AXE, Items.STONE_AXE,
                        Items.IRON_AXE, Items.GOLDEN_AXE,
                        Items.DIAMOND_AXE, Items.NETHERITE_AXE)
                      .build())
                    .build())
                  .build()))
          .with(ItemEntry.builder(Items.LEATHER))
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(config.axe_leather_range.getMin(),config.axe_leather_range.getMax())).build());

        // COW KILLED WITH SWORD - LEATHER
        LootPool.Builder leatherSwordBuilder = LootPool.builder()
          .conditionally(
            EntityPropertiesLootCondition
              .builder(LootContext.EntityTarget.KILLER,
                new EntityPredicate.Builder()
                  .equipment(EntityEquipmentPredicate.Builder.create()
                    .mainhand(ItemPredicate.Builder.create()
                      .items(Items.WOODEN_SWORD, Items.STONE_SWORD,
                        Items.IRON_SWORD, Items.GOLDEN_SWORD,
                        Items.DIAMOND_SWORD,
                        Items.NETHERITE_SWORD)
                      .build())
                    .build())
                  .build()))
          .with(ItemEntry.builder(Items.LEATHER))
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(config.sword_leather_range.getMin(), config.sword_leather_range.getMax())).build());

        // COW KILLED WITH SWORD - BEEF
        LootPool.Builder beefSwordBuilder = LootPool.builder()
          .conditionally(
            EntityPropertiesLootCondition
              .builder(LootContext.EntityTarget.KILLER,
                new EntityPredicate.Builder()
                  .equipment(EntityEquipmentPredicate.Builder.create()
                    .mainhand(ItemPredicate.Builder.create()
                      .items(Items.WOODEN_SWORD, Items.STONE_SWORD,
                        Items.IRON_SWORD, Items.GOLDEN_SWORD,
                        Items.DIAMOND_SWORD,
                        Items.NETHERITE_SWORD)
                      .build())
                    .build())
                  .build()))
          .with(ItemEntry.builder(Items.BEEF))
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(config.sword_beef_range.getMin(), config.sword_beef_range.getMax())).build());

        return LootTable.builder()
          .pool(beefDefaultBuilder)
          .pool(leatherDefaultBuilder)
          .pool(leatherSwordBuilder)
          .pool(beefAxeBuilder)
          .pool(beefSwordBuilder)
          .pool(leatherAxeBuilder)
          .build();
      }
      return null;
    });
    LOGGER.info("Beef & Blade Loaded!");
  }
}
