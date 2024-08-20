
package net.mcreator.repairamulet.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class BasicRepairAmuletItem extends Item {
	public BasicRepairAmuletItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
}
