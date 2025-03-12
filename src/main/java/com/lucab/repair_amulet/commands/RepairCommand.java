package com.lucab.repair_amulet.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class RepairCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("repair_item").requires(s -> s.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.players())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0, Integer.MAX_VALUE))
                                .executes(arguments -> {
                                    EntityArgument.getEntities(arguments, "players").forEach(entity -> {
                                        if (entity instanceof Player _player && !_player.level().isClientSide) {
                                            ItemStack item = _player.getItemInHand(_player.getUsedItemHand());
                                            if (Item.getId(item.getItem()) == Item.getId(ItemStack.EMPTY.getItem())) {
                                                _player.displayClientMessage(Component.literal("No item selected"),
                                                        false);
                                            } else if (!item.isDamageableItem()) {
                                                _player.displayClientMessage(
                                                        Component.literal("Item is not damageable"),
                                                        false);
                                            } else {
                                                item.setDamageValue(
                                                        item.getDamageValue() - arguments.getArgument(
                                                                "amount", Integer.class));
                                            }
                                        }
                                    });

                                    return 0;
                                }))));
    }
}
