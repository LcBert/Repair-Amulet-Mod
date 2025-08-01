package com.lucab.repair_amulet;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.loading.FMLPaths;

@EventBusSubscriber
public class ReloadConfig {
    @SubscribeEvent
    public static void onReload(AddReloadListenerEvent event) {
        Utils.config = ConfigSchema.load(FMLPaths.CONFIGDIR.get().resolve(Utils.mod_id + ".json").toFile());
    }
}