package net.mcreator.repairamulet.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.mcreator.repairamulet.network.RepairAmuletModVariables;
import net.mcreator.repairamulet.configuration.RepairAmuletConfigFileConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ReadConfigFileProcedure {
	@SubscribeEvent
	public static void onWorldLoad(net.minecraftforge.event.level.LevelEvent.Load event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		RepairAmuletModVariables.tick_list.clear();
		RepairAmuletModVariables.amount_list.clear();
		RepairAmuletModVariables.item_list.clear();
		RepairAmuletModVariables.tick_list.add(((double) RepairAmuletConfigFileConfiguration.BASIC_TICK.get()));
		RepairAmuletModVariables.tick_list.add(((double) RepairAmuletConfigFileConfiguration.ADVANCED_TICK.get()));
		RepairAmuletModVariables.tick_list.add(((double) RepairAmuletConfigFileConfiguration.ELITE_TICK.get()));
		RepairAmuletModVariables.tick_list.add(((double) RepairAmuletConfigFileConfiguration.ULTIMATE_TICK.get()));
		RepairAmuletModVariables.tick_list.add((-1));
		RepairAmuletModVariables.amount_list.add(((double) RepairAmuletConfigFileConfiguration.BASIC_AMOUNT.get()));
		RepairAmuletModVariables.amount_list.add(((double) RepairAmuletConfigFileConfiguration.ADVANCED_AMOUNT.get()));
		RepairAmuletModVariables.amount_list.add(((double) RepairAmuletConfigFileConfiguration.ELITE_AMOUNT.get()));
		RepairAmuletModVariables.amount_list.add(((double) RepairAmuletConfigFileConfiguration.ULTIMATE_AMOUNT.get()));
		RepairAmuletModVariables.amount_list.add((-1));
		for (String stringiterator : RepairAmuletConfigFileConfiguration.ITEM_LIST.get()) {
			RepairAmuletModVariables.item_list.add(stringiterator);
		}
		RepairAmuletModVariables.list_is_blacklist = RepairAmuletConfigFileConfiguration.LIST_TYPE.get();
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u00A76Repair Amulet - Config Reloaded"), false);
	}
}
