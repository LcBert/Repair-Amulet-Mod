package net.mcreator.repairamulet.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.level.BlockEvent;

import net.minecraft.world.entity.Entity;

import net.mcreator.repairamulet.network.RepairAmuletModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CheckPlayerMinedProcedure {
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		execute(event, event.getPlayer());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		{
			boolean _setval = false;
			entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.player_is_mining = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
