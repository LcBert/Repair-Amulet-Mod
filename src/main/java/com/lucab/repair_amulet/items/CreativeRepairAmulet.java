package com.lucab.repair_amulet.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class CreativeRepairAmulet extends Item {
    public CreativeRepairAmulet() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }
}
