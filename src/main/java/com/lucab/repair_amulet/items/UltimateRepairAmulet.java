package com.lucab.repair_amulet.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class UltimateRepairAmulet extends Item {
    public UltimateRepairAmulet() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }
}
