package com.lucab.repair_amulet;

import java.util.ArrayList;

import net.minecraft.world.item.ItemStack;

public class Functions {
    public static String getNamespace(ItemStack item) {
        String descriptionId = item.getDescriptionId().toString();
        String[] splitDesc = descriptionId.split("\\.");
        return String.format("%s:%s", splitDesc[1], splitDesc[2]);
    }

    public static boolean listContains(Object list, ItemStack item) {
        for (Object obj : (ArrayList<?>) list) {
            if (obj instanceof String) {
                if (String.valueOf(obj).equals(getNamespace(item))) {
                    return true;
                }
            }
        }
        return false;
    }
}
