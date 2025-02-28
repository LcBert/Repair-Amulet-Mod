package com.lucab.repair_amulet.procedures;

import javax.annotation.Nullable;

import com.lucab.repair_amulet.network.ModVariables;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class ResetPlayerVariables {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity == null)
            return;

        if (entity instanceof Player _player && !_player.level().isClientSide()) {
            _player.getData(ModVariables.PLAYER_VARIABLES).amulet_is_running = false;
            _player.getData(ModVariables.PLAYER_VARIABLES).syncPlayerVariables(_player);
        }
    }
}
