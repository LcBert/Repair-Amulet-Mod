package com.lucab.repair_amulet.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EliteRepairAmulet extends Item {
    public EliteRepairAmulet() {
        super(new Item.Properties().rarity(Rarity.RARE).stacksTo(1));
    }
}
