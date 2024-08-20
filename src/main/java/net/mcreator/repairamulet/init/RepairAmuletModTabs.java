
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.repairamulet.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.repairamulet.RepairAmuletMod;

public class RepairAmuletModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RepairAmuletMod.MODID);
	public static final RegistryObject<CreativeModeTab> REPAIR_AMULET_TAB = REGISTRY.register("repair_amulet_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.repair_amulet.repair_amulet_tab")).icon(() -> new ItemStack(RepairAmuletModItems.ELITE_REPAIR_AMULET.get())).displayItems((parameters, tabData) -> {
				tabData.accept(RepairAmuletModItems.BASIC_REPAIR_AMULET.get());
				tabData.accept(RepairAmuletModItems.ADVANCED_REPAIR_AMULET.get());
				tabData.accept(RepairAmuletModItems.ELITE_REPAIR_AMULET.get());
				tabData.accept(RepairAmuletModItems.ULTIMATE_REPAIR_AMULET.get());
				tabData.accept(RepairAmuletModItems.CREATIVE_REPAIR_AMULET.get());
			})

					.build());
}
