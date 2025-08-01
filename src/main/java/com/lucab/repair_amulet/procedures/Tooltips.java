package com.lucab.repair_amulet.procedures;

import java.util.List;

import javax.annotation.Nullable;

import com.lucab.repair_amulet.Utils;
import com.lucab.repair_amulet.items.ItemsRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

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
                    Component.translatable("tooltip.repair_amulet.basic",
                            Utils.config.BasicAmount,
                            Utils.config.BasicTick));

        if (itemstack.getItem() == ItemsRegistry.ADVANCED_REPAIR_AMULET.get())
            tooltip.add(1,
                    Component.translatable("tooltip.repair_amulet.advanced",
                            Utils.config.AdvancedAmount,
                            Utils.config.AdvancedTick));

        if (itemstack.getItem() == ItemsRegistry.ELITE_REPAIR_AMULET.get())
            tooltip.add(1,
                    Component.translatable("tooltip.repair_amulet.elite",
                            Utils.config.EliteAmount,
                            Utils.config.EliteTick));

        if (itemstack.getItem() == ItemsRegistry.ULTIMATE_REPAIR_AMULET.get())
            tooltip.add(1,
                    Component.translatable("tooltip.repair_amulet.ultimate",
                            Utils.config.UltimateAmount,
                            Utils.config.UltimateTick));

        if (itemstack.getItem() == ItemsRegistry.CREATIVE_REPAIR_AMULET.get())
            tooltip.add(1, Component.translatable("tooltip.repair_amulet.creative"));
    }
}
