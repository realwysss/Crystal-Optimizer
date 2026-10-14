/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_3222
 *  net.minecraft.class_8710
 */
package truefulys.optimizer.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_3222;
import net.minecraft.class_8710;
import truefulys.optimizer.TruefulysCrystalOptimizerMod;
import truefulys.optimizer.client.ServerOptOutState;
import truefulys.optimizer.command.EnableOptimizerCommand;
import truefulys.optimizer.network.OptimizerControlPayload;
import truefulys.optimizer.server.ServerOptOutConfig;

public final class OptimizerNetworking {
    private static boolean payloadRegistered;

    private OptimizerNetworking() {
    }

    public static void registerPayloadType() {
        if (payloadRegistered) {
            return;
        }
        payloadRegistered = true;
        PayloadTypeRegistry.playS2C().register(OptimizerControlPayload.ID, OptimizerControlPayload.CODEC);
    }

    public static void registerServerHandlers() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> ServerPlayNetworking.send((class_3222)handler.field_14140, (class_8710)new OptimizerControlPayload(ServerOptOutConfig.get().allowCrystalOptimizer)));
    }

    public static void registerClientHandlers() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!client.method_1496()) {
                ServerOptOutState.onJoinMultiplayer();
            }
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ServerOptOutState.reset();
            TruefulysCrystalOptimizerMod.resetSessionState();
        });
        ClientPlayNetworking.registerGlobalReceiver(OptimizerControlPayload.ID, (payload, context) -> context.client().execute(() -> {
            ServerOptOutState.setServerAllowed(payload.allowed());
            if (!payload.allowed()) {
                EnableOptimizerCommand.displayMessage("Truefuly's Crystal Optimizer is disabled on this server by server policy.");
            }
        }));
    }
}

