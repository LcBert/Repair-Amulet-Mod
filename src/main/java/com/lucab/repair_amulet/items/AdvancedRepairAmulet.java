package com.lucab.repair_amulet.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class AdvancedRepairAmulet extends Item {
    public AdvancedRepairAmulet() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1));
    }
}
