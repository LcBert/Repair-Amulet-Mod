
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.repairamulet.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import net.mcreator.repairamulet.item.UltimateRepairAmuletItem;
import net.mcreator.repairamulet.item.EliteRepairAmuletItem;
import net.mcreator.repairamulet.item.CreativeRepairAmuletItem;
import net.mcreator.repairamulet.item.BasicRepairAmuletItem;
import net.mcreator.repairamulet.item.AdvancedRepairAmuletItem;
import net.mcreator.repairamulet.RepairAmuletMod;

public class RepairAmuletModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, RepairAmuletMod.MODID);
	public static final RegistryObject<Item> BASIC_REPAIR_AMULET = REGISTRY.register("basic_repair_amulet", () -> new BasicRepairAmuletItem());
	public static final RegistryObject<Item> ADVANCED_REPAIR_AMULET = REGISTRY.register("advanced_repair_amulet", () -> new AdvancedRepairAmuletItem());
	public static final RegistryObject<Item> ELITE_REPAIR_AMULET = REGISTRY.register("elite_repair_amulet", () -> new EliteRepairAmuletItem());
	public static final RegistryObject<Item> ULTIMATE_REPAIR_AMULET = REGISTRY.register("ultimate_repair_amulet", () -> new UltimateRepairAmuletItem());
	public static final RegistryObject<Item> CREATIVE_REPAIR_AMULET = REGISTRY.register("creative_repair_amulet", () -> new CreativeRepairAmuletItem());
	// Start of user code block custom items
	// End of user code block custom items
}
