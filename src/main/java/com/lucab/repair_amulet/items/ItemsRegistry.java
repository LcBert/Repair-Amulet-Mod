package com.lucab.repair_amulet.items;

import com.lucab.repair_amulet.Utils;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegistry {
        public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister
                        .create(ForgeRegistries.ITEMS, Utils.mod_id);

        public static final RegistryObject<Item> UNBREAKING_CORE = ITEM_REGISTRY
                        .register("unbreaking_core", UnbreakingCore::new);

        public static final RegistryObject<Item> BASIC_REPAIR_AMULET = ITEM_REGISTRY
                        .register("basic_repair_amulet", BasicRepairAmulet::new);

        public static final RegistryObject<Item> ADVANCED_REPAIR_AMULET = ITEM_REGISTRY
                        .register("advanced_repair_amulet", AdvancedRepairAmulet::new);

        public static final RegistryObject<Item> ELITE_REPAIR_AMULET = ITEM_REGISTRY
                        .register("elite_repair_amulet", EliteRepairAmulet::new);

        public static final RegistryObject<Item> ULTIMATE_REPAIR_AMULET = ITEM_REGISTRY
                        .register("ultimate_repair_amulet", UltimateRepairAmulet::new);

        public static final RegistryObject<Item> CREATIVE_REPAIR_AMULET = ITEM_REGISTRY
                        .register("creative_repair_amulet", CreativeRepairAmulet::new);
}
