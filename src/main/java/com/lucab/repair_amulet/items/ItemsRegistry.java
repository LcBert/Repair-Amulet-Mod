package com.lucab.repair_amulet.items;

import com.lucab.repair_amulet.main;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemsRegistry {
        public static final DeferredRegister.Items ITEM_REGISTRY = DeferredRegister.createItems(main.MODID);

        public static final DeferredItem<Item> BASIC_REPAIR_AMULET = ITEM_REGISTRY
                        .register("basic_repair_amulet", BasicRepairAmulet::new);

        public static final DeferredItem<Item> ADVANCED_REPAIR_AMULET = ITEM_REGISTRY
                        .register("advanced_repair_amulet", AdvancedRepairAmulet::new);

        public static final DeferredItem<Item> ELITE_REPAIR_AMULET = ITEM_REGISTRY
                        .register("elite_repair_amulet", EliteRepairAmulet::new);

        public static final DeferredItem<Item> ULTIMATE_REPAIR_AMULET = ITEM_REGISTRY
                        .register("ultimate_repair_amulet", UltimateRepairAmulet::new);

        public static final DeferredItem<Item> CREATIVE_REPAIR_AMULET = ITEM_REGISTRY
                        .register("creative_repair_amulet", CreativeRepairAmulet::new);
}
