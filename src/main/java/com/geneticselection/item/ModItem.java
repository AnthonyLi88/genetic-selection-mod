package com.geneticselection.item;

import com.geneticselection.GeneticSelection;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItem {

    public static final Item THING_1 = registerItem("thing_1", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(GeneticSelection.MOD_ID, name), item);
    }

    public static void registerModItems(){
        GeneticSelection.LOGGER.info("Registering Mod Items for "+GeneticSelection.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(THING_1);
        });
    }
}
