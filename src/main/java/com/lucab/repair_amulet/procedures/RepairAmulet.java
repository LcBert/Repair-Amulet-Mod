package com.lucab.repair_amulet.procedures;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.lucab.repair_amulet.Utils;
import com.lucab.repair_amulet.main;
import com.lucab.repair_amulet.network.ModVariables;
import com.lucab.repair_amulet.Functions;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;
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

    // private static boolean listContains(Object list, ItemStack item) {
    // for (Object obj : (ArrayList<?>) list) {
    // if (obj instanceof String) {
    // if (String.valueOf(obj).equals(getNamespace(item))) {
    // return true;
    // }
    // }
    // }
    // return false;
    // }

    // private static String getNamespace(ItemStack item) {
    // String descriptionId = item.getDescriptionId().toString();
    // String[] splitDesc = descriptionId.split("\\.");
    // return String.format("%s:%s", splitDesc[1], splitDesc[2]);
    // }

    private static void repair_item(Player player, ItemStack item, int amount) {
        boolean can_repair = true;
        Object config_list = Utils.config.ItemsList.get("List").get("Value");
        if (item.isDamageableItem()) {
            if (player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                    .orElse(new ModVariables.PlayerVariables()).amulet_in_inventory != "creative") {
                if (List.of(config_list).size() > 0) {
                    if (!(boolean) Utils.config.ItemsList.get("Blacklist").get("Value")) {
                        can_repair = Functions.listContains(config_list, item);
                    } else {
                        can_repair = !Functions.listContains(config_list, item);
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
                cost = (int) (long) Utils.config.BasicRepairAmulet.get("Cost").get("Value");
                break;

            case "advanced":
                cost = (int) (long) Utils.config.AdvancedRepairAmulet.get("Cost").get("Value");
                break;

            case "elite":
                cost = (int) (long) Utils.config.EliteRepairAmulet.get("Cost").get("Value");
                break;

            case "ultimate":
                cost = (int) (long) Utils.config.UltimateRepairAmulet.get("Cost").get("Value");
                break;

            default:
                break;
        }

        switch ((int) (long) Utils.config.RepairCost.get("Value")) {
            case 1: // Consume Item
                AtomicInteger item_count = new AtomicInteger(0);
                ItemStack itemCost = new ItemStack(
                        ForgeRegistries.ITEMS.getValue(
                                ResourceLocation.tryParse((String) Utils.config.RepairCostItem.get("Value"))));

                if (player.getInventory().contains(itemCost)) {
                    player.getInventory().items.forEach(item -> {
                        if (Functions.getNamespace(item).equals(Functions.getNamespace(itemCost))) {
                            item_count.addAndGet(item.getCount());
                        }
                    });
                    if (Functions.getNamespace(player.getOffhandItem()).equals(Functions.getNamespace(itemCost))) {
                        item_count.addAndGet(player.getOffhandItem().getCount());
                    }
                }

                if (item_count.get() >= cost) {
                    for (int i = 1; i <= cost; i++)
                        player.getInventory().clearOrCountMatchingItems(p -> itemCost.getItem() == p.getItem(), 1,
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
