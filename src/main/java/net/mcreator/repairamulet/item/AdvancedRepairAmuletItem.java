
package net.mcreator.repairamulet.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class AdvancedRepairAmuletItem extends Item implements ICurioItem {
	public AdvancedRepairAmuletItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
}
