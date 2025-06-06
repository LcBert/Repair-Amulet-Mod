package com.lucab.repair_amulet.mixin;

import com.lucab.repair_amulet.items.ItemsRegistry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class AnvilUnbreakingCore {
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        ItemStack output = left.copy();

        if (right.is(ItemsRegistry.UNBREAKING_CORE.get()) &&
                !left.getTag().contains("Unbreakable", (byte) 1) &&
                left.getMaxDamage() > 0) {
            output.setDamageValue(0);

            CompoundTag tag = new CompoundTag();
            tag.putByte("Unbreakable", (byte) 1);
            output.setTag(tag);

            if (!event.getName().isEmpty())
                output.setHoverName(Component.literal(event.getName()));

            event.setCost(30);
            event.setOutput(output);
        }
    }
}
