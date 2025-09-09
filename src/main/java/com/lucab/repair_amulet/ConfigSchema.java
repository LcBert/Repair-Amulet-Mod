package com.lucab.repair_amulet;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.annotations.Expose;

public class ConfigSchema {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .create();

    @Expose
    public Map<String, Object> RepairCost = new HashMap<String, Object>() {
        {
            put("//", "Define the cost for every reparations[0: None, 1: Item, 2: XP-Points, 3: XP-Levels]");
            put("Value", 0);
        }
    };

    @Expose
    public Map<String, Object> RepairCostItem = new HashMap<String, Object>() {
        {
            put("//", "Define the item to use as repair cost (Only if Repair Cost is set to 1)");
            put("Value", "");
        }
    };

    @Expose
    public Map<String, HashMap<String, Object>> BasicRepairAmulet = new HashMap<String, HashMap<String, Object>>() {
        {
            put("Tick", new HashMap<String, Object>() {
                {
                    put("//", "Repair delay for Basic Repair Amulet (Default: 100)");
                    put("Value", 100);
                }
            });
            put("Amount", new HashMap<String, Object>() {
                {
                    put("//", "Repair amount for Basic Repair Amulet (Default: 1)");
                    put("Value", 1);
                }
            });
            put("Cost", new HashMap<String, Object>() {
                {
                    put("//", "Repair cost for Basic Repair Amulet (Default: 0)");
                    put("Value", 0);
                }
            });
        }
    };

    @Expose
    public Map<String, HashMap<String, Object>> AdvancedRepairAmulet = new HashMap<String, HashMap<String, Object>>() {
        {
            put("Tick", new HashMap<String, Object>() {
                {
                    put("//", "Repair delay for Advanced Repair Amulet (Default: 50)");
                    put("Value", 50);
                }
            });
            put("Amount", new HashMap<String, Object>() {
                {
                    put("//", "Repair amount for Advanced Repair Amulet (Default: 1)");
                    put("Value", Integer.valueOf(1));
                }
            });
            put("Cost", new HashMap<String, Object>() {
                {
                    put("//", "Repair cost for Advanced Repair Amulet (Default: 0)");
                    put("Value", 0);
                }
            });
        }
    };

    @Expose
    public Map<String, HashMap<String, Object>> EliteRepairAmulet = new HashMap<String, HashMap<String, Object>>() {
        {
            put("Tick", new HashMap<String, Object>() {
                {
                    put("//", "Repair delay for Elite Repair Amulet (Default: 20)");
                    put("Value", 20);
                }
            });
            put("Amount", new HashMap<String, Object>() {
                {
                    put("//", "Repair amount for Elite Repair Amulet (Default: 1)");
                    put("Value", 1);
                }
            });
            put("Cost", new HashMap<String, Object>() {
                {
                    put("//", "Repair cost for Elite Repair Amulet (Default: 0)");
                    put("Value", 0);
                }
            });
        }
    };

    @Expose
    public Map<String, HashMap<String, Object>> UltimateRepairAmulet = new HashMap<String, HashMap<String, Object>>() {
        {
            put("Tick", new HashMap<String, Object>() {
                {
                    put("//", "Repair delay for Ultimate Repair Amulet (Default: 5)");
                    put("Value", 5);
                }
            });
            put("Amount", new HashMap<String, Object>() {
                {
                    put("//", "Repair amount for Ultimate Repair Amulet (Default: 1)");
                    put("Value", 1);
                }
            });
            put("Cost", new HashMap<String, Object>() {
                {
                    put("//", "Repair cost for Ultimate Repair Amulet (Default: 0)");
                    put("Value", 0);
                }
            });
        }
    };

    @Expose
    public Map<String, HashMap<String, Object>> ItemsList = new HashMap<String, HashMap<String, Object>>() {
        {
            put("List", new HashMap<String, Object>() {
                {
                    put("//", "Items list to consider during repair");
                    put("Value", List.of());
                }
            });
            put("Blacklist", new HashMap<String, Object>() {
                {
                    put("//", "Define if the list is blacklist");
                    put("Value", true);
                }
            });
        }
    };

    @Expose
    public Map<String, HashMap<String, Object>> UnbreakingCore = new HashMap<String, HashMap<String, Object>>() {
        {
            put("Cost", new HashMap<String, Object>() {
                {
                    put("//", "Define the cost in XP Level for the Unbreaking Core (0: No Cost, -1: Disable)");
                    put("Value", 30);
                }
            });
            put("List", new HashMap<String, Object>() {
                {
                    put("//", "Items list to consider for Unbreaking Core");
                    put("Value", List.of());
                }
            });
            put("Blacklist", new HashMap<String, Object>() {
                {
                    put("//", "Define if the list is blacklist");
                    put("Value", true);
                }
            });
        }
    };

    public static ConfigSchema load(File configFile) {
        ConfigSchema config = new ConfigSchema();
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                config = GSON.fromJson(reader, ConfigSchema.class);
            } catch (Exception e) {
            }
        } else {
            configFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {
        }

        return config;
    }
}
