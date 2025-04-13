package com.lucab.repair_amulet.procedures;

import java.util.concurrent.atomic.AtomicInteger;

import com.lucab.repair_amulet.Config;
import com.lucab.repair_amulet.main;
import com.lucab.repair_amulet.network.ModVariables;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RepairAmulet {
    public static void execute(Player player) {
        if (player == null)
            return;
        if (!player.level().isClientSide()) {
            if (!player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                    .orElse(new ModVariables.PlayerVariables()).amulet_is_running
                    && player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                            .orElse(new ModVariables.PlayerVariables()).have_amulet
                    && !player.isDeadOrDying()) {

                player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                        .orElse(new ModVariables.PlayerVariables()).amulet_is_running = true;
                player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                        .orElse(new ModVariables.PlayerVariables()).syncPlayerVariables(player);

                int repair_tick_value = player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                        .orElse(new ModVariables.PlayerVariables()).repair_tick_value;

                int repair_amount_value = player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                        .orElse(new ModVariables.PlayerVariables()).repair_amount_value;

                // Items in Inventory
                player.getInventory().items.forEach(item -> {
                    repair_item(player, item, repair_amount_value);
                });

                // Armor Items
                player.getArmorSlots().forEach(item -> {
                    repair_item(player, item, repair_amount_value);
                });

                // Items in Offhand
                repair_item(player, player.getOffhandItem(), repair_amount_value);

                if (repair_tick_value != -1)
                    main.queueServerWork(repair_tick_value, () -> {
                        reset_running(player);
                    });
                else {
                    reset_running(player);
                }
            }
        }
    }

    private static void reset_running(Player player) {
        player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                .orElse(new ModVariables.PlayerVariables()).amulet_is_running = false;
        player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                .orElse(new ModVariables.PlayerVariables()).syncPlayerVariables(player);
    }

    private static void repair_item(Player player, ItemStack item, int amount) {
        boolean can_repair = true;
        if (item.isDamageableItem()) {
            if (player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                    .orElse(new ModVariables.PlayerVariables()).amulet_in_inventory != "creative") {
                if (Config.items_list.size() > 0 && Config.items_list.toArray()[0] != "") {
                    if (!Config.list_blacklist) {
                        can_repair = Config.items_list.contains(item.getItem());
                    } else {
                        can_repair = !Config.items_list.contains(item.getItem());
                    }
                }
            }

            if (can_repair && item.getDamageValue() > 0) {
                if (amount != -1) {
                    if (consume_cost(player)) {
                        item.setDamageValue(item.getDamageValue() - amount);
                    }
                } else {
                    item.setDamageValue(0);
                }
            }
        }
    }

    private static boolean consume_cost(Player player) {
        int cost = 0;

        switch (player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                .orElse(new ModVariables.PlayerVariables()).amulet_in_inventory) {
            case "basic":
                cost = Config.basic_cost_amount;
                break;

            case "advanced":
                cost = Config.advanced_cost_amount;
                break;

            case "elite":
                cost = Config.elite_cost_amount;
                break;

            case "ultimate":
                cost = Config.ultimate_cost_amount;
                break;

            default:
                break;
        }

        switch (Config.repair_cost) {
            case 1: // Consume Item
                AtomicInteger item_count = new AtomicInteger(0);
                if (player.getInventory().contains(new ItemStack(Config.repair_cost_item))) {
                    player.getInventory().items.forEach(item -> {
                        if (item.toString().contains(Config.repair_cost_item.toString())) {
                            item_count.addAndGet(item.getCount());
                        }
                    });
                    if (player.getOffhandItem().toString().contains(Config.repair_cost_item.toString())) {
                        item_count.addAndGet(player.getOffhandItem().getCount());
                    }
                }

                if (item_count.get() >= cost) {
                    ItemStack _stktoremove = new ItemStack(Config.repair_cost_item);
                    for (int i = 1; i <= cost; i++)
                        player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1,
                                player.inventoryMenu.getCraftSlots());
                    return true;
                }
                return false;

            case 2:
                // Effective player experience
                int player_xp_points = 0;
                // Player Experience for current level
                int player_level_xp_points = Mth.floor(player.experienceProgress * player.getXpNeededForNextLevel());

                for (int i = 1; i <= player.experienceLevel; i++) {
                    player_xp_points += 7 + ((i - 1) * 2);
                }
                player_xp_points += player_level_xp_points;

                if (player_xp_points >= cost) {
                    player.giveExperiencePoints(-cost);
                    return true;
                }
                return false;

            case 3:
                if (player.experienceLevel >= cost) {
                    player.giveExperienceLevels(cost * -1);
                    return true;
                }
                return false;

            default:
                break;
        }
        return true;
    }
}
