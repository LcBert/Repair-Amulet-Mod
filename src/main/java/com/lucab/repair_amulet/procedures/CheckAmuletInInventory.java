package com.lucab.repair_amulet.procedures;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.lucab.repair_amulet.Utils;
import com.lucab.repair_amulet.items.ItemsRegistry;
import com.lucab.repair_amulet.network.ModVariables;
import com.mojang.authlib.yggdrasil.request.TelemetryEventsRequest.Event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class CheckAmuletInInventory {
    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            for (Entity entityiterator : new ArrayList<>(event.level.players())) {
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
                putValues(_player,
                        "ultimate",
                        true,
                        (int) (long) Utils.config.UltimateRepairAmulet.get("Tick").get("Value"),
                        (int) (long) Utils.config.UltimateRepairAmulet.get("Amount").get("Value"));
            } else if (_player.getInventory().contains(new ItemStack(ItemsRegistry.ELITE_REPAIR_AMULET.get()))) {
                putValues(_player,
                        "elite",
                        true,
                        (int) (long) Utils.config.EliteRepairAmulet.get("Tick").get("Value"),
                        (int) (long) Utils.config.EliteRepairAmulet.get("Amount").get("Value"));
            } else if (_player.getInventory().contains(new ItemStack(ItemsRegistry.ADVANCED_REPAIR_AMULET.get()))) {
                putValues(_player,
                        "advanced",
                        true,
                        (int) (long) Utils.config.AdvancedRepairAmulet.get("Tick").get("Value"),
                        (int) (long) Utils.config.AdvancedRepairAmulet.get("Amount").get("Value"));
            } else if (_player.getInventory().contains(new ItemStack(ItemsRegistry.BASIC_REPAIR_AMULET.get()))) {
                putValues(_player,
                        "basic",
                        true,
                        (int) (long) Utils.config.BasicRepairAmulet.get("Tick").get("Value"),
                        (int) (long) Utils.config.BasicRepairAmulet.get("Amount").get("Value"));
            } else {
                putValues(_player, "none", false, 0, 0);
            }
            if ((_player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                    .orElse(new ModVariables.PlayerVariables())).have_amulet)
                RepairAmulet.execute(_player);
        }
    }

    private static void putValues(Player player, String amulet, boolean have_amulet, int tick, int amount) {
        player.getCapability(ModVariables.PLAYER_VARIABLES, null).ifPresent(capability -> {
            capability.amulet_in_inventory = amulet;
            capability.have_amulet = have_amulet;
            capability.repair_tick_value = tick;
            capability.repair_amount_value = amount;
            capability.syncPlayerVariables(player);
        });
    }
}
