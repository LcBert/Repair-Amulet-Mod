package net.mcreator.repairamulet.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.mcreator.repairamulet.network.RepairAmuletModVariables;
import net.mcreator.repairamulet.configuration.RepairAmuletConfigFileConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ReadConfigFileProcedure {
	@SubscribeEvent
	public static void onWorldLoad(net.minecraftforge.event.level.LevelEvent.Load event) {
		execute(event);
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
		RepairAmuletModVariables.tick_list.clear();
		RepairAmuletModVariables.amount_list.clear();
		RepairAmuletModVariables.amulet_blacklist.clear();
		RepairAmuletModVariables.amulet_whitelist.clear();
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
		for (String stringiterator : RepairAmuletConfigFileConfiguration.AMULET_BLACKLIST.get()) {
			RepairAmuletModVariables.amulet_blacklist.add(stringiterator);
		}
		for (String stringiterator : RepairAmuletConfigFileConfiguration.AMULET_WHITELIST.get()) {
			RepairAmuletModVariables.amulet_whitelist.add(stringiterator);
		}
	}
}
