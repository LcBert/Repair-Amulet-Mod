package com.lucab.repair_amulet;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ConfigSchema {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Expose
    public int RepairCost = 0;
    @Expose
    public String RepairCostItem = "";
    @Expose
    public int BasicTick = 100;
    @Expose
    public int BasicAmount = 1;
    @Expose
    public int BasicCost = 0;
    @Expose
    public int AdvancedTick = 50;
    @Expose
    public int AdvancedAmount = 1;
    @Expose
    public int AdvancedCost = 0;
    @Expose
    public int EliteTick = 20;
    @Expose
    public int EliteAmount = 1;
    @Expose
    public int EliteCost = 0;
    @Expose
    public int UltimateTick = 5;
    @Expose
    public int UltimateAmount = 1;
    @Expose
    public int UltimateCost = 0;
    @Expose
    public boolean ListBlacklist = true;
    @Expose
    public String[] ItemsList = new String[0];

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
