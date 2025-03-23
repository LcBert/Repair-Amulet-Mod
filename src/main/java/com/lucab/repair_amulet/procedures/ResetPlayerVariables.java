package com.lucab.repair_amulet.procedures;

import javax.annotation.Nullable;

import com.lucab.repair_amulet.network.ModVariables;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

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
            _player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                    .orElse(new ModVariables.PlayerVariables()).amulet_is_running = false;
            _player.getCapability(ModVariables.PLAYER_VARIABLES, null)
                    .orElse(new ModVariables.PlayerVariables()).syncPlayerVariables(_player);
        }
    }
}
