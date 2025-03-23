package com.lucab.repair_amulet.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class BasicRepairAmulet extends Item implements ICurioItem {
    public BasicRepairAmulet() {
        super(new Item.Properties().rarity(Rarity.COMMON).stacksTo(1));
    }
}