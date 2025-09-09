package com.lucab.repair_amulet.mixin;

import java.util.List;

import com.lucab.repair_amulet.Utils;
import com.lucab.repair_amulet.items.ItemsRegistry;
import com.lucab.repair_amulet.Functions;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
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
        Integer cost = Integer.valueOf(Utils.config.UnbreakingCore.get("Cost").get("Value").toString());

        if (cost <= 0)
            return;

        boolean can_upgrade = true;
        Object config_list = Utils.config.UnbreakingCore.get("List").get("Value");

        if (List.of(config_list).size() > 0) {
            if (!(boolean) Utils.config.UnbreakingCore.get("Blacklist").get("Value")) {
                can_upgrade = Functions.listContains(config_list, left);
            } else {
                can_upgrade = !Functions.listContains(config_list, left);
            }
        }

        if (can_upgrade &&
                right.is(ItemsRegistry.UNBREAKING_CORE) &&
                !left.has(DataComponents.UNBREAKABLE) &&
                left.has(DataComponents.MAX_DAMAGE)) {

            output.setDamageValue(0);
            output.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
            if (!event.getName().isEmpty()) {
                output.set(DataComponents.CUSTOM_NAME, Component.literal(event.getName()));
                cost++;
            }

            event.setCost(cost);
            event.setOutput(output);
        }
    }
}
