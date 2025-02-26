package com.lucab.repair_amulet.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import java.util.function.Supplier;

import com.lucab.repair_amulet.main;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModVariables {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister
            .create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, main.MODID);
    public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES
            .register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        main.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC,
                PlayerVariablesSyncMessage::handleData);
    }

    @EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
            PlayerVariables clone = new PlayerVariables();
            clone.amulet_is_running = original.amulet_is_running;
            if (!event.isWasDeath()) {
            }
            event.getEntity().setData(PLAYER_VARIABLES, clone);
        }
    }

    public static class PlayerVariables implements INBTSerializable<CompoundTag> {
        public boolean amulet_is_running = false;
        public boolean have_amulet = false;
        public int repair_tick_value = 0;
        public int repair_amount_value = 0;

        @SuppressWarnings("null")
        @Override
        public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
            CompoundTag nbt = new CompoundTag();
            nbt.putBoolean("amulet_is_running", amulet_is_running);
            nbt.putBoolean("have_amulet", have_amulet);
            nbt.putInt("repair_tick_value", repair_tick_value);
            nbt.putInt("repair_amount_value", repair_amount_value);
            return nbt;
        }

        @SuppressWarnings("null")
        @Override
        public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
            amulet_is_running = nbt.getBoolean("amulet_is_running");
            have_amulet = nbt.getBoolean("have_amulet");
            repair_tick_value = nbt.getInt("repair_tick_value");
            repair_amount_value = nbt.getInt("repair_amount_value");
        }

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer)
                PacketDistributor.sendToPlayer(serverPlayer, new PlayerVariablesSyncMessage(this));
        }
    }

    public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
        public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(
                ResourceLocation.fromNamespaceAndPath(main.MODID, "player_variables_sync"));
        public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
                .of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer
                        .writeNbt(message.data().serializeNBT(buffer.registryAccess())),
                        (RegistryFriendlyByteBuf buffer) -> {
                            PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
                            message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
                            return message;
                        });

        @Override
        public Type<PlayerVariablesSyncMessage> type() {
            return TYPE;
        }

        public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
            if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
                context.enqueueWork(() -> context.player().getData(PLAYER_VARIABLES).deserializeNBT(
                        context.player().registryAccess(),
                        message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
                            context.connection().disconnect(Component.literal(e.getMessage()));
                            return null;
                        });
            }
        }
    }
}
