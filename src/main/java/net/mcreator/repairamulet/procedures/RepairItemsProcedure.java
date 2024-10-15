package net.mcreator.repairamulet.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.repairamulet.network.RepairAmuletModVariables;
import net.mcreator.repairamulet.RepairAmuletMod;

import javax.annotation.Nullable;

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class RepairItemsProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player.level(), event.player);
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		boolean can_repair = false;
		double selected_slot_number = 0;
		double slot_number_iterator = 0;
		double i = 0;
		double j = 0;
		if ((entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new RepairAmuletModVariables.PlayerVariables())).tick_to_wait != -1) {
			if ((entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new RepairAmuletModVariables.PlayerVariables())).repair_amulet_can_work) {
				selected_slot_number = new Object() {
					public double getValue() {
						CompoundTag dataIndex = new CompoundTag();
						entity.saveWithoutId(dataIndex);
						return dataIndex.getDouble("SelectedItemSlot");
					}
				}.getValue();
				slot_number_iterator = 0;
				if (!(entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new RepairAmuletModVariables.PlayerVariables())).repair_amulet_is_working) {
					{
						boolean _setval = true;
						entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.repair_amulet_is_working = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						AtomicReference<IItemHandler> _iitemhandlerref = new AtomicReference<>();
						entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(_iitemhandlerref::set);
						if (_iitemhandlerref.get() != null) {
							for (int _idx = 0; _idx < _iitemhandlerref.get().getSlots(); _idx++) {
								ItemStack itemstackiterator = _iitemhandlerref.get().getStackInSlot(_idx).copy();
								if (!(slot_number_iterator == selected_slot_number)) {
									if (itemstackiterator.getOrCreateTag().getDouble("Damage") > 0) {
										if (RepairAmuletModVariables.list_is_blacklist) {
											can_repair = true;
											i = 0;
											for (int index0 = 0; index0 < (int) RepairAmuletModVariables.item_list.size(); index0++) {
												if (itemstackiterator.getItem() == ForgeRegistries.ITEMS
														.getValue(new ResourceLocation(((RepairAmuletModVariables.item_list.get((int) i) instanceof String _s ? _s : "")).toLowerCase(java.util.Locale.ENGLISH)))) {
													can_repair = false;
													break;
												}
												i = i + 1;
											}
										} else {
											can_repair = false;
											i = 0;
											for (int index1 = 0; index1 < (int) RepairAmuletModVariables.item_list.size(); index1++) {
												if (itemstackiterator.getItem() == ForgeRegistries.ITEMS
														.getValue(new ResourceLocation(((RepairAmuletModVariables.item_list.get((int) i) instanceof String _s ? _s : "")).toLowerCase(java.util.Locale.ENGLISH)))) {
													can_repair = true;
													break;
												}
												i = i + 1;
											}
										}
										if (can_repair) {
											{
												ItemStack _ist = itemstackiterator;
												if (_ist.hurt((int) ((entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new RepairAmuletModVariables.PlayerVariables())).amout_to_repair * (-1)),
														RandomSource.create(), null)) {
													_ist.shrink(1);
													_ist.setDamageValue(0);
												}
											}
											{
												final int _slotid = (int) slot_number_iterator;
												final ItemStack _setstack = itemstackiterator.copy();
												_setstack.setCount(1);
												entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
													if (capability instanceof IItemHandlerModifiable _modHandlerEntSetSlot)
														_modHandlerEntSetSlot.setStackInSlot(_slotid, _setstack);
												});
											}
										}
									}
								}
								slot_number_iterator = slot_number_iterator + 1;
							}
						}
					}
					RepairAmuletMod.queueServerWork((int) (entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new RepairAmuletModVariables.PlayerVariables())).tick_to_wait, () -> {
						{
							boolean _setval = false;
							entity.getCapability(RepairAmuletModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.repair_amulet_is_working = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
					});
				}
			}
		}
	}
}
