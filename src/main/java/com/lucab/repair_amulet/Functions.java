package com.lucab.repair_amulet;

import java.util.ArrayList;

import net.minecraft.world.item.ItemStack;

public class Functions {
    public static boolean listContains(Object list, ItemStack item) {
        for (Object obj : (ArrayList<?>) list) {
            if (obj instanceof String) {
                if (String.valueOf(obj).equals(item.getItem().toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
