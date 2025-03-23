package com.lucab.repair_amulet.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BasicRepairAmulet extends Item {
    public BasicRepairAmulet() {
        super(new Item.Properties().rarity(Rarity.COMMON).stacksTo(1));
    }
}