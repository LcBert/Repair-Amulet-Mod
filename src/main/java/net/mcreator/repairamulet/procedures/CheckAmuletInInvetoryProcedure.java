package net.mcreator.repairamulet.procedures;

import top.theillusivec4.curios.api.CuriosApi;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.repairamulet.network.RepairAmuletModVariables;
import net.mcreator.repairamulet.init.RepairAmuletModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CheckAmuletInInvetoryProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		double value = 0;
		if ((entity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(RepairAmuletModItems.CREATIVE_REPAIR_AMULET.get())) : false)
				|| (entity instanceof LivingEntity lv ? CuriosApi.getCuriosHelper().findEquippedCurio(RepairAmuletModItems.CREATIVE_REPAIR_AMULET.get(), lv).isPresent() : false == true)) {
			{
				boolean _setval = false;
				entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.repair_amulet_can_work = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			value = 4;
		} else if ((entity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(RepairAmuletModItems.ULTIMATE_REPAIR_AMULET.get())) : false)
				|| (entity instanceof LivingEntity lv ? CuriosApi.getCuriosHelper().findEquippedCurio(RepairAmuletModItems.ULTIMATE_REPAIR_AMULET.get(), lv).isPresent() : false == true)) {
			{
				boolean _setval = true;
				entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.repair_amulet_can_work = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			value = 3;
		} else if ((entity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(RepairAmuletModItems.ELITE_REPAIR_AMULET.get())) : false)
				|| (entity instanceof LivingEntity lv ? CuriosApi.getCuriosHelper().findEquippedCurio(RepairAmuletModItems.ELITE_REPAIR_AMULET.get(), lv).isPresent() : false == true)) {
			{
				boolean _setval = true;
				entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.repair_amulet_can_work = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			value = 2;
		} else if ((entity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(RepairAmuletModItems.ADVANCED_REPAIR_AMULET.get())) : false)
				|| (entity instanceof LivingEntity lv ? CuriosApi.getCuriosHelper().findEquippedCurio(RepairAmuletModItems.ADVANCED_REPAIR_AMULET.get(), lv).isPresent() : false == true)) {
			{
				boolean _setval = true;
				entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.repair_amulet_can_work = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			value = 1;
		} else if ((entity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(RepairAmuletModItems.BASIC_REPAIR_AMULET.get())) : false)
				|| (entity instanceof LivingEntity lv ? CuriosApi.getCuriosHelper().findEquippedCurio(RepairAmuletModItems.BASIC_REPAIR_AMULET.get(), lv).isPresent() : false == true)) {
			{
				boolean _setval = true;
				entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.repair_amulet_can_work = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			value = 0;
		} else {
			{
				boolean _setval = false;
				entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.repair_amulet_can_work = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		if ((entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new RepairAmuletModVariables.PlayerVariables())).repair_amulet_can_work) {
			{
				double _setval = RepairAmuletModVariables.tick_list.get((int) value) instanceof Double _d ? _d : 0;
				entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.tick_to_wait = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = RepairAmuletModVariables.amount_list.get((int) value) instanceof Double _d ? _d : 0;
				entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.amout_to_repair = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
