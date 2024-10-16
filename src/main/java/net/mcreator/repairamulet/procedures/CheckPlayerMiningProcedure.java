package net.mcreator.repairamulet.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.entity.Entity;

import net.mcreator.repairamulet.network.RepairAmuletModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CheckPlayerMiningProcedure {
	@SubscribeEvent
	public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		{
			boolean _setval = !(entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new RepairAmuletModVariables.PlayerVariables())).player_is_mining;
			entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.player_is_mining = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
