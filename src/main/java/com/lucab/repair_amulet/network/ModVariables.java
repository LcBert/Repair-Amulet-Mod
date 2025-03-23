package com.lucab.repair_amulet.network;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

import com.lucab.repair_amulet.main;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModVariables {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        main.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer,
                PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
    }

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent event) {
        event.register(PlayerVariables.class);
    }

    @Mod.EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES, null)
                        .orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES, null)
                        .orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES, null)
                        .orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            event.getOriginal().revive();
            PlayerVariables original = ((PlayerVariables) event.getOriginal()
                    .getCapability(PLAYER_VARIABLES, null).orElse(new PlayerVariables()));
            PlayerVariables clone = ((PlayerVariables) event.getEntity()
                    .getCapability(PLAYER_VARIABLES, null).orElse(new PlayerVariables()));
            clone.amulet_is_running = original.amulet_is_running;
            if (!event.isWasDeath()) {
            }
        }
    }

    public static final Capability<PlayerVariables> PLAYER_VARIABLES = CapabilityManager
            .get(new CapabilityToken<PlayerVariables>() {
            });

    @Mod.EventBusSubscriber
    private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
                event.addCapability(new ResourceLocation("repair_amulet", "player_variables"),
                        new PlayerVariablesProvider());
        }

        private final PlayerVariables playerVariables = new PlayerVariables();
        private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == PLAYER_VARIABLES ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public Tag serializeNBT() {
            return playerVariables.writeNBT();
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            playerVariables.readNBT(nbt);
        }
    }

    public static class PlayerVariables {
        public boolean amulet_is_running = false;
        public String amulet_in_inventory = "";
        public boolean have_amulet = false;
        public int repair_tick_value = 0;
        public int repair_amount_value = 0;

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer)
                main.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
                        new PlayerVariablesSyncMessage(this));
        }

        public Tag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putBoolean("amulet_is_running", amulet_is_running);
            nbt.putString("amulet_in_inventory", amulet_in_inventory);
            nbt.putBoolean("have_amulet", have_amulet);
            nbt.putInt("repair_tick_value", repair_tick_value);
            nbt.putInt("repair_amount_value", repair_amount_value);
            return nbt;
        }

        public void readNBT(Tag tag) {
            CompoundTag nbt = (CompoundTag) tag;
            amulet_is_running = nbt.getBoolean("amulet_is_running");
            amulet_in_inventory = nbt.getString("amulet_in_inventory");
            have_amulet = nbt.getBoolean("have_amulet");
            repair_tick_value = nbt.getInt("repair_tick_value");
            repair_amount_value = nbt.getInt("repair_amount_value");
        }
    }

    public static class PlayerVariablesSyncMessage {
        private final PlayerVariables data;

        public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
            this.data = new PlayerVariables();
            this.data.readNBT(buffer.readNbt());
        }

        public PlayerVariablesSyncMessage(PlayerVariables data) {
            this.data = data;
        }

        public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
            buffer.writeNbt((CompoundTag) message.data.writeNBT());
        }

        public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player
                            .getCapability(PLAYER_VARIABLES, null).orElse(new PlayerVariables()));
                    variables.amulet_is_running = message.data.amulet_is_running;
                }
            });
            context.setPacketHandled(true);
        }
    }
}
