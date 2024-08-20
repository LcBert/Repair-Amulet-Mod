package net.mcreator.repairamulet.procedures;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.nbt.CompoundTag;

import java.util.concurrent.atomic.AtomicReference;

public class CreativeRepairAmuletTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double selected_slot_number = 0;
		double slot_number_iterator = 0;
		selected_slot_number = new Object() {
			public double getValue() {
				CompoundTag dataIndex = new CompoundTag();
				entity.saveWithoutId(dataIndex);
				return dataIndex.getDouble("SelectedItemSlot");
			}
		}.getValue();
		slot_number_iterator = 0;
		{
			AtomicReference<IItemHandler> _iitemhandlerref = new AtomicReference<>();
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(_iitemhandlerref::set);
			if (_iitemhandlerref.get() != null) {
				for (int _idx = 0; _idx < _iitemhandlerref.get().getSlots(); _idx++) {
					ItemStack itemstackiterator = _iitemhandlerref.get().getStackInSlot(_idx).copy();
					if (!(slot_number_iterator == selected_slot_number)) {
						if (itemstackiterator.getOrCreateTag().getDouble("Damage") > 0) {
							itemstackiterator.setDamageValue(0);
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
					slot_number_iterator = slot_number_iterator + 1;
				}
			}
		}
	}
}
