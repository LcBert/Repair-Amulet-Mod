package net.mcreator.repairamulet.procedures;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.mcreator.repairamulet.network.RepairAmuletModVariables;
import net.mcreator.repairamulet.configuration.RepairAmuletConfigFileConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReadConfigFileProcedure {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		execute();
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
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
	}
}
