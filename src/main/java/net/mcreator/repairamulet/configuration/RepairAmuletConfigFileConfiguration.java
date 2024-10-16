package net.mcreator.repairamulet.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class RepairAmuletConfigFileConfiguration {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	public static final ForgeConfigSpec.ConfigValue<Double> BASIC_TICK;
	public static final ForgeConfigSpec.ConfigValue<Double> BASIC_AMOUNT;
	public static final ForgeConfigSpec.ConfigValue<Double> ADVANCED_TICK;
	public static final ForgeConfigSpec.ConfigValue<Double> ADVANCED_AMOUNT;
	public static final ForgeConfigSpec.ConfigValue<Double> ELITE_TICK;
	public static final ForgeConfigSpec.ConfigValue<Double> ELITE_AMOUNT;
	public static final ForgeConfigSpec.ConfigValue<Double> ULTIMATE_TICK;
	public static final ForgeConfigSpec.ConfigValue<Double> ULTIMATE_AMOUNT;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_LIST;
	public static final ForgeConfigSpec.ConfigValue<Boolean> LIST_TYPE;
	static {
		BUILDER.push("Basic Repair Amulet");
		BASIC_TICK = BUILDER.comment("How many tick have to wait before repair items").define("tick", (double) 100);
		BASIC_AMOUNT = BUILDER.comment("How many damage point will the amulet remove").define("amount", (double) 1);
		BUILDER.pop();
		BUILDER.push("Advanced Repair Amulet");
		ADVANCED_TICK = BUILDER.comment("How many tick have to wait before repair items").define("tick", (double) 50);
		ADVANCED_AMOUNT = BUILDER.comment("How many damage point will the amulet remove").define("amount", (double) 1);
		BUILDER.pop();
		BUILDER.push("Elite Repair Amulet");
		ELITE_TICK = BUILDER.comment("How many tick have to wait before repair items").define("tick", (double) 20);
		ELITE_AMOUNT = BUILDER.comment("How many damage point will the amulet remove").define("amount", (double) 1);
		BUILDER.pop();
		BUILDER.push("Ultimate Repair Amulet");
		ULTIMATE_TICK = BUILDER.comment("How many tick have to wait before repair items").define("tick", (double) 5);
		ULTIMATE_AMOUNT = BUILDER.comment("How many damage point will the amulet remove").define("amount", (double) 1);
		BUILDER.pop();
		BUILDER.push("Repair List");
		ITEM_LIST = BUILDER.comment("List of items that will be considered when repair").defineList("item_list", List.of(), entry -> true);
		LIST_TYPE = BUILDER.comment("Specify the type of list\nDefault: true").define("list_is_blacklist", true);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
