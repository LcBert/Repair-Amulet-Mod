package net.mcreator.repairamulet.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import net.mcreator.repairamulet.network.RepairAmuletModVariables;
import net.mcreator.repairamulet.init.RepairAmuletModItems;

import javax.annotation.Nullable;

import java.util.List;

@Mod.EventBusSubscriber
public class RepairAmuletTooltipProcedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		execute(event, event.getItemStack(), event.getToolTip());
	}

	public static void execute(ItemStack itemstack, List<Component> tooltip) {
		execute(null, itemstack, tooltip);
	}

	private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
		if (tooltip == null)
			return;
		if (itemstack.getItem() == RepairAmuletModItems.BASIC_REPAIR_AMULET.get()) {
			tooltip.add(1,
					Component.literal(("Repair "
							+ (("" + (RepairAmuletModVariables.amount_list.get(0) instanceof Double _d ? _d : 0)).substring(0, (int) (("" + (RepairAmuletModVariables.amount_list.get(0) instanceof Double _d ? _d : 0)).length() - 2))) + " every "
							+ (("" + (RepairAmuletModVariables.tick_list.get(0) instanceof Double _d ? _d : 0)).substring(0, (int) (("" + (RepairAmuletModVariables.tick_list.get(0) instanceof Double _d ? _d : 0)).length() - 2))) + " tick")));
		} else if (itemstack.getItem() == RepairAmuletModItems.ADVANCED_REPAIR_AMULET.get()) {
			tooltip.add(1,
					Component.literal(("Repair "
							+ (("" + (RepairAmuletModVariables.amount_list.get(1) instanceof Double _d ? _d : 0)).substring(0, (int) (("" + (RepairAmuletModVariables.amount_list.get(1) instanceof Double _d ? _d : 0)).length() - 2))) + " every "
							+ (("" + (RepairAmuletModVariables.tick_list.get(1) instanceof Double _d ? _d : 0)).substring(0, (int) (("" + (RepairAmuletModVariables.tick_list.get(1) instanceof Double _d ? _d : 0)).length() - 2))) + " tick")));
		} else if (itemstack.getItem() == RepairAmuletModItems.ELITE_REPAIR_AMULET.get()) {
			tooltip.add(1,
					Component.literal(("Repair "
							+ (("" + (RepairAmuletModVariables.amount_list.get(2) instanceof Double _d ? _d : 0)).substring(0, (int) (("" + (RepairAmuletModVariables.amount_list.get(2) instanceof Double _d ? _d : 0)).length() - 2))) + " every "
							+ (("" + (RepairAmuletModVariables.tick_list.get(2) instanceof Double _d ? _d : 0)).substring(0, (int) (("" + (RepairAmuletModVariables.tick_list.get(2) instanceof Double _d ? _d : 0)).length() - 2))) + " tick")));
		} else if (itemstack.getItem() == RepairAmuletModItems.ULTIMATE_REPAIR_AMULET.get()) {
			tooltip.add(1,
					Component.literal(("Repair "
							+ (("" + (RepairAmuletModVariables.amount_list.get(3) instanceof Double _d ? _d : 0)).substring(0, (int) (("" + (RepairAmuletModVariables.amount_list.get(3) instanceof Double _d ? _d : 0)).length() - 2))) + " every "
							+ (("" + (RepairAmuletModVariables.tick_list.get(3) instanceof Double _d ? _d : 0)).substring(0, (int) (("" + (RepairAmuletModVariables.tick_list.get(3) instanceof Double _d ? _d : 0)).length() - 2))) + " tick")));
		} else if (itemstack.getItem() == RepairAmuletModItems.CREATIVE_REPAIR_AMULET.get()) {
			tooltip.add(1, Component.literal("Repair items immediately"));
		}
	}
}
