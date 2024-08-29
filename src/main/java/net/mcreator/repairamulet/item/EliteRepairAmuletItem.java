
package net.mcreator.repairamulet.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class EliteRepairAmuletItem extends Item implements ICurioItem {
	public EliteRepairAmuletItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
}
