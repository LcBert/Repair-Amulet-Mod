package com.lucab.repair_amulet;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.lucab.repair_amulet.items.ItemsRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod(Utils.mod_id)
public class main {
    // public static final String MODID = "repair_amulet";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, Utils.mod_id);

    public static final RegistryObject<CreativeModeTab> CREATIVE_MOD_TABS = CREATIVE_TAB
            .register("repair_amulet", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.creative_tab"))
                    .icon(() -> ItemsRegistry.ADVANCED_REPAIR_AMULET.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        ItemsRegistry.ITEM_REGISTRY.getEntries().forEach(entry -> {
                            output.accept(entry.get());
                        });
                    }).build());

    public main(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ItemsRegistry.ITEM_REGISTRY.register(modEventBus);
        CREATIVE_TAB.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        // context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        Utils.config = ConfigSchema.load(FMLPaths.CONFIGDIR.get().resolve(Utils.mod_id + ".json").toFile());
    }

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Utils.mod_id, Utils.mod_id), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder,
            Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tick, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
            workQueue.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() == 0)
                    actions.add(work);
            });
            actions.forEach(e -> e.getKey().run());
            workQueue.removeAll(actions);
        }
    }
}
