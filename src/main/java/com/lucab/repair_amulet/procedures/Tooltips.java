package com.lucab.repair_amulet.procedures;

import java.util.List;

import javax.annotation.Nullable;

import com.lucab.repair_amulet.Config;
import com.lucab.repair_amulet.items.ItemsRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(value = { Dist.CLIENT })
public class Tooltips {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        execute(event, event.getItemStack(), event.getToolTip());
    }

    public static void execute(ItemStack itemstack, List<Component> tooltip) {
        execute(null, itemstack, tooltip);
    }

    public static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
        if (tooltip == null)
            return;

        if (itemstack.getItem() == ItemsRegistry.BASIC_REPAIR_AMULET.get())
            tooltip.add(1,
                    Component.translatable("tooltip.repair_amulet.basic", Config.basic_amount, Config.basic_tick));

        if (itemstack.getItem() == ItemsRegistry.ADVANCED_REPAIR_AMULET.get())
            tooltip.add(1,
                    Component.translatable("tooltip.repair_amulet.advanced", Config.advanced_amount,
                            Config.advanced_tick));

        if (itemstack.getItem() == ItemsRegistry.ELITE_REPAIR_AMULET.get())
            tooltip.add(1,
                    Component.translatable("tooltip.repair_amulet.elite", Config.elite_amount, Config.elite_tick));

        if (itemstack.getItem() == ItemsRegistry.ULTIMATE_REPAIR_AMULET.get())
            tooltip.add(1,
                    Component.translatable("tooltip.repair_amulet.ultimate", Config.ultimate_amount,
                            Config.ultimate_tick));

        if (itemstack.getItem() == ItemsRegistry.CREATIVE_REPAIR_AMULET.get())
            tooltip.add(1, Component.translatable("tooltip.repair_amulet.creative"));
    }
}
