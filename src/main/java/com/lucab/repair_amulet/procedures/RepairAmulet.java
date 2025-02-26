package com.lucab.repair_amulet.procedures;

import com.lucab.repair_amulet.main;
import com.lucab.repair_amulet.network.ModVariables;

import net.minecraft.world.entity.player.Player;

public class RepairAmulet {
    public static void execute(Player player) {
        if (player == null)
            return;
        if (!player.level().isClientSide()) {
            if (!player.getData(ModVariables.PLAYER_VARIABLES).amulet_is_running
                    && player.getData(ModVariables.PLAYER_VARIABLES).have_amulet) {
                player.getData(ModVariables.PLAYER_VARIABLES).amulet_is_running = true;
                player.getData(ModVariables.PLAYER_VARIABLES).syncPlayerVariables(player);

                int repair_tick_value = player.getData(ModVariables.PLAYER_VARIABLES).repair_tick_value;
                int repair_amount_value = player.getData(ModVariables.PLAYER_VARIABLES).repair_amount_value;

                player.getInventory().items.forEach(item -> {
                    if (item.isDamageableItem())
                        item.setDamageValue(
                                repair_amount_value != -1 ? (item.getDamageValue() - repair_amount_value) : 0);
                });

                if (repair_tick_value != -1)
                    main.queueServerWork(repair_tick_value, () -> {
                        player.getData(ModVariables.PLAYER_VARIABLES).amulet_is_running = false;
                        player.getData(ModVariables.PLAYER_VARIABLES).syncPlayerVariables(player);
                    });
                else {
                    player.getData(ModVariables.PLAYER_VARIABLES).amulet_is_running = false;
                    player.getData(ModVariables.PLAYER_VARIABLES).syncPlayerVariables(player);
                }
            }
        }
    }
}