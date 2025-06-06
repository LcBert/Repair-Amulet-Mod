package com.lucab.repair_amulet.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class UnbreakingCore extends Item {
    public UnbreakingCore() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }
}
