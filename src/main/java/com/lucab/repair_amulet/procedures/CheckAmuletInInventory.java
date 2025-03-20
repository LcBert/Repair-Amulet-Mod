package com.lucab.repair_amulet.procedures;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.lucab.repair_amulet.Config;
import com.lucab.repair_amulet.items.ItemsRegistry;
import com.lucab.repair_amulet.network.ModVariables;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber
public class CheckAmuletInInventory {
    @SubscribeEvent
    public static void onWorldTick(LevelTickEvent.Post event) {
        for (Entity entityiterator : new ArrayList<>(event.getLevel().players())) {
            execute(entityiterator);
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    public static void execute(@Nullable Event event, Entity entity) {
        if (entity == null)
            return;

        if (entity instanceof Player _player) {
            if (_player.getInventory().contains(new ItemStack(ItemsRegistry.CREATIVE_REPAIR_AMULET.get()))) {
                putValues(_player, "creative", true, -1, -1);
            } else if (_player.getInventory().contains(new ItemStack(ItemsRegistry.ULTIMATE_REPAIR_AMULET.get()))) {
                putValues(_player, "ultimate", true, Config.ultimate_tick, Config.ultimate_amount);
            } else if (_player.getInventory().contains(new ItemStack(ItemsRegistry.ELITE_REPAIR_AMULET.get()))) {
                putValues(_player, "elite", true, Config.elite_tick, Config.elite_amount);
            } else if (_player.getInventory().contains(new ItemStack(ItemsRegistry.ADVANCED_REPAIR_AMULET.get()))) {
                putValues(_player, "advanced", true, Config.advanced_tick, Config.advanced_amount);
            } else if (_player.getInventory().contains(new ItemStack(ItemsRegistry.BASIC_REPAIR_AMULET.get()))) {
                putValues(_player, "basic", true, Config.basic_tick, Config.basic_amount);
            } else {
                putValues(_player, "none", false, 0, 0);
            }
            if (_player.getData(ModVariables.PLAYER_VARIABLES).have_amulet)
                RepairAmulet.execute(_player);
        }
    }

    private static void putValues(Player player, String amulet, boolean have_amulet, int tick, int amount) {
        player.getData(ModVariables.PLAYER_VARIABLES).amulet_in_inventory = amulet;
        player.getData(ModVariables.PLAYER_VARIABLES).have_amulet = have_amulet;
        player.getData(ModVariables.PLAYER_VARIABLES).repair_tick_value = tick;
        player.getData(ModVariables.PLAYER_VARIABLES).repair_amount_value = amount;
        player.getData(ModVariables.PLAYER_VARIABLES).syncPlayerVariables(player);
    }
}
