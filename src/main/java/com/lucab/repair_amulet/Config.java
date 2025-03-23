package com.lucab.repair_amulet;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
        private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        private static final ForgeConfigSpec.IntValue BasicTickValue = BUILDER
                        .push("Basic Repair Amulet")
                        .comment("Repair delay for Basic Repair Amulet")
                        .defineInRange("BasicTick", 100, 0, Integer.MAX_VALUE);

        private static final ForgeConfigSpec.IntValue BasicAmountValue = BUILDER
                        .comment("Repair amount for Basic Repair Amulet")
                        .defineInRange("BasicAmount", 1, 0, Integer.MAX_VALUE);

        private static final ForgeConfigSpec.IntValue AdvancedTickValue = BUILDER
                        .pop()
                        .push("Advanced Repair Amulet")
                        .comment("Repair delay for Advanced Repair Amulet")
                        .defineInRange("AdvancedTick", 50, 0, Integer.MAX_VALUE);

        private static final ForgeConfigSpec.IntValue AdvancedAmountValue = BUILDER
                        .comment("Repair amount for Advanced Repair Amulet")
                        .defineInRange("AdvancedAmount", 1, 0, Integer.MAX_VALUE);

        private static final ForgeConfigSpec.IntValue EliteTickValue = BUILDER
                        .pop()
                        .push("Elite Repair Amulet")
                        .comment("Repair delay for Elite Repair Amulet")
                        .defineInRange("EliteTick", 20, 0, Integer.MAX_VALUE);

        private static final ForgeConfigSpec.IntValue EliteAmountValue = BUILDER
                        .comment("Repair amount for Elite Repair Amulet")
                        .defineInRange("EliteAmount", 1, 0, Integer.MAX_VALUE);

        private static final ForgeConfigSpec.IntValue UltimateTickValue = BUILDER
                        .pop()
                        .push("Ultimate Repair Amulet")
                        .comment("Repair delay for Ultimate Repair Amulet")
                        .defineInRange("UltimateTick", 5, 0, Integer.MAX_VALUE);

        private static final ForgeConfigSpec.IntValue UltimateAmountValue = BUILDER
                        .comment("Repair amount for Ultimate Repair Amulet")
                        .defineInRange("UltimateAmount", 1, 0, Integer.MAX_VALUE);

        private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ItemsListValue = BUILDER
                        .pop()
                        .push("Items List")
                        .comment("Items list to consider during repair")
                        .defineListAllowEmpty("Items", List.of(""), Config::validateItem);

        private static final ForgeConfigSpec.BooleanValue ListBlacklist = BUILDER
                        .comment("Whether the items list is a blacklist or whitelist")
                        .define("Blacklist", true);

        static final ForgeConfigSpec SPEC = BUILDER.build();

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
                                .map(itemName -> ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(itemName)))
                                .collect(Collectors.toSet());
        }

        public static boolean validateItem(final Object obj) {
                return (obj instanceof String itemName
                                && ForgeRegistries.ITEMS.containsKey(ResourceLocation.parse(itemName)));
        }
}
