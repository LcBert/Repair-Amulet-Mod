
package net.mcreator.repairamulet.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;

import net.mcreator.repairamulet.procedures.CreativeRepairAmuletTickProcedure;

public class CreativeRepairAmuletItem extends Item implements ICurioItem {
	public CreativeRepairAmuletItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		CreativeRepairAmuletTickProcedure.execute(world, entity);
	}
}
