package net.mcreator.repairamulet.init;

import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.mcreator.repairamulet.configuration.RepairAmuletConfigFileConfiguration;
import net.mcreator.repairamulet.RepairAmuletMod;

@Mod.EventBusSubscriber(modid = RepairAmuletMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RepairAmuletModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RepairAmuletConfigFileConfiguration.SPEC, "repair_amulet-common.toml");
		});
	}
}
