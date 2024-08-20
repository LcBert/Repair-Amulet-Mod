
package net.mcreator.repairamulet.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class EliteRepairAmuletItem extends Item {
	public EliteRepairAmuletItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
	}
}
