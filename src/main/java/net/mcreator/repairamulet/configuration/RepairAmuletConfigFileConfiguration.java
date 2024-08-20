package net.mcreator.repairamulet.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

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
	static {
		BUILDER.push("basic_repair_amulet");
		BASIC_TICK = BUILDER.comment("How many tick have to wait before repair items").define("tick", (double) 100);
		BASIC_AMOUNT = BUILDER.comment("How many damage point will the ring remove").define("amount", (double) 1);
		BUILDER.pop();
		BUILDER.push("advanced_repair_amulet");
		ADVANCED_TICK = BUILDER.comment("How many tick have to wait before repair items").define("tick", (double) 50);
		ADVANCED_AMOUNT = BUILDER.comment("How many damage point will the ring remove").define("amount", (double) 1);
		BUILDER.pop();
		BUILDER.push("elite_repair_amulet");
		ELITE_TICK = BUILDER.comment("How many tick have to wait before repair items").define("tick", (double) 20);
		ELITE_AMOUNT = BUILDER.comment("How many damage point will the ring remove").define("amount", (double) 1);
		BUILDER.pop();
		BUILDER.push("ultimate_repair_amulet");
		ULTIMATE_TICK = BUILDER.comment("How many tick have to wait before repair items").define("tick", (double) 5);
		ULTIMATE_AMOUNT = BUILDER.comment("How many damage point will the ring remove").define("amount", (double) 1);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
