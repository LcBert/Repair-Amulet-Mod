package com.lucab.repair_amulet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        private static final ModConfigSpec.IntValue BasicTickValue = BUILDER
                        .push("Basic Repair Amulet")
                        .comment("Repair delay for Basic Repair Amulet")
                        .defineInRange("Tick", 200, 0, Integer.MAX_VALUE);

        private static final ModConfigSpec.IntValue BasicAmountValue = BUILDER
                        .comment("Repair amount for Basic Repair Amulet")
                        .defineInRange("Amount", 1, 0, Integer.MAX_VALUE);

        private static final ModConfigSpec.IntValue AdvancedTickValue = BUILDER
                        .pop()
                        .push("Advanced Repair Amulet")
                        .comment("Repair delay for Advanced Repair Amulet")
                        .defineInRange("Tick", 100, 0, Integer.MAX_VALUE);

        private static final ModConfigSpec.IntValue AdvancedAmountValue = BUILDER
                        .comment("Repair amount for Advanced Repair Amulet")
                        .defineInRange("Amount", 2, 0, Integer.MAX_VALUE);

        private static final ModConfigSpec.IntValue EliteTickValue = BUILDER
                        .pop()
                        .push("Elite Repair Amulet")
                        .comment("Repair delay for Elite Repair Amulet")
                        .defineInRange("Tick", 50, 0, Integer.MAX_VALUE);

        private static final ModConfigSpec.IntValue EliteAmountValue = BUILDER
                        .comment("Repair amount for Elite Repair Amulet")
                        .defineInRange("Amount", 4, 0, Integer.MAX_VALUE);

        private static final ModConfigSpec.IntValue UltimateTickValue = BUILDER
                        .pop()
                        .push("Ultimate Repair Amulet")
                        .comment("Repair delay for Ultimate Repair Amulet")
                        .defineInRange("Tick", 25, 0, Integer.MAX_VALUE);

        private static final ModConfigSpec.IntValue UltimateAmountValue = BUILDER
                        .comment("Repair amount for Ultimate Repair Amulet")
                        .defineInRange("Amount", 8, 0, Integer.MAX_VALUE);

        private static final ModConfigSpec.ConfigValue<List<? extends String>> ItemsListValue = BUILDER
                        .pop()
                        .push("Items List")
                        .comment("Items list to consider during repair")
                        // .defineListAllowEmpty("Items", List.of(""), Config::validateItem);
                        .defineListAllowEmpty("Items", List.of(""), null, Config::validateItem);

        private static final ModConfigSpec.BooleanValue ListBlacklist = BUILDER
                        .comment("Define if the items list is blacklist")
                        .define("Blacklist", true);

        static final ModConfigSpec SPEC = BUILDER.build();

        public static int basic_tick;
        public static int basic_amount;
        public static int advanced_tick;
        public static int advanced_amount;
        public static int elite_tick;
        public static int elite_amount;
        public static int ultimate_tick;
        public static int ultimate_amount;
        public static Set<Item> items_list;
        public static boolean list_blacklist;

        @SubscribeEvent
        static void onLoad(final ModConfigEvent event) {
                basic_tick = BasicTickValue.get();
                basic_amount = BasicAmountValue.get();
                advanced_tick = AdvancedTickValue.get();
                advanced_amount = AdvancedAmountValue.get();
                elite_tick = EliteTickValue.get();
                elite_amount = EliteAmountValue.get();
                ultimate_tick = UltimateTickValue.get();
                ultimate_amount = UltimateAmountValue.get();
                list_blacklist = ListBlacklist.get();

                items_list = ItemsListValue.get().stream()
                                .map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName)))
                                .collect(Collectors.toSet());
        }

        public static boolean validateItem(final Object obj) {
                return (obj instanceof String itemName
                                && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName)));
        }
}
