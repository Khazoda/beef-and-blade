package com.seacroak.beefandblade;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeefAndBlade implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("beef-and-blade");

    private static final Identifier COW_LOOT_TABLE_ID = EntityType.COW.getLootTableId();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
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

                // COW KILLED WITH AXE
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
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 5.0f)).build());

                // COW KILLED WITH SWORD
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
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 3.0f)).build());

                return LootTable.builder().pool(beefDefaultBuilder).pool(leatherDefaultBuilder)
                        .pool(leatherSwordBuilder)
                        .pool(beefAxeBuilder).build();
            }
            return null;
        });
        LOGGER.info("Beef & Blade Loaded!");
    }
}
