package com.lucab.repair_amulet.mixin;

import com.lucab.repair_amulet.items.ItemsRegistry;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber
public class AnvilUnbreakingCore {
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        ItemStack output = left.copy();

        if (right.is(ItemsRegistry.UNBREAKING_CORE) &&
                !left.has(DataComponents.UNBREAKABLE) &&
                left.has(DataComponents.MAX_DAMAGE)) {

            output.setDamageValue(0);
            output.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
            if (!event.getName().isEmpty())
                output.set(DataComponents.CUSTOM_NAME, Component.literal(event.getName()));

            output.set(DataComponents.LORE,
                    new ItemLore(java.util.List
                            .of(Component.translatable("text.repair_amulet.anvil.forge_unbreaking_core")
                                    .withColor(0xFF0000))));
            
            event.setOutput(output);

            if (event.getName().isEmpty())
                event.setCost(30);
            else
                event.setCost(31);
        }
    }
}
