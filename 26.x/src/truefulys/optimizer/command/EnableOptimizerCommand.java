/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
 *  net.fabricmc.fabric.api.client.command.v2.ClientCommands
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 */
package truefulys.optimizer.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import truefulys.optimizer.client.ServerOptOutState;

public class EnableOptimizerCommand {
    public static boolean fastCrystal = true;

    public static boolean isOptimizerActive() {
        return fastCrystal && !ServerOptOutState.isServerOptedOut();
    }

    public void initializeToggleCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)ClientCommands.literal((String)"truefulysoptimizer").then(ClientCommands.literal((String)"enable").executes(context -> {
            if (ServerOptOutState.isServerOptedOut()) {
                EnableOptimizerCommand.displayMessage("Cannot enable: this server has disabled the Crystal Optimizer.");
                return 0;
            }
            fastCrystal = true;
            EnableOptimizerCommand.displayMessage("Truefulys Crystal Optimizer enabled");
            return 1;
        }))).then(ClientCommands.literal((String)"disable").executes(context -> {
            fastCrystal = false;
            EnableOptimizerCommand.displayMessage("Truefulys Crystal Optimizer disabled!");
            return 1;
        }))));
    }

    public static void displayMessage(String message) {
        if (!EnableOptimizerCommand.inGame().booleanValue()) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        client.player.sendSystemMessage((Component)Component.literal((String)message));
    }

    public static Boolean inGame() {
        Minecraft client = Minecraft.getInstance();
        return client.player != null && client.getConnection() != null;
    }
}

