/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
 *  net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_338
 */
package truefulys.optimizer.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_338;
import truefulys.optimizer.client.ServerOptOutState;

public class EnableOptimizerCommand {
    public static boolean fastCrystal = true;

    public static boolean isOptimizerActive() {
        return fastCrystal && !ServerOptOutState.isServerOptedOut();
    }

    public void initializeToggleCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)ClientCommandManager.literal((String)"truefulysoptimizer").then(ClientCommandManager.literal((String)"enable").executes(context -> {
            if (ServerOptOutState.isServerOptedOut()) {
                EnableOptimizerCommand.displayMessage("Cannot enable: this server has disabled the Crystal Optimizer.");
                return 0;
            }
            fastCrystal = true;
            EnableOptimizerCommand.displayMessage("Truefulys Crystal Optimizer enabled");
            return 1;
        }))).then(ClientCommandManager.literal((String)"disable").executes(context -> {
            fastCrystal = false;
            EnableOptimizerCommand.displayMessage("Truefulys Crystal Optimizer disabled!");
            return 1;
        }))));
    }

    public static void displayMessage(String message) {
        if (!EnableOptimizerCommand.inGame().booleanValue()) {
            return;
        }
        class_310 client = class_310.method_1551();
        class_338 chatHud = client.field_1705.method_1743();
        chatHud.method_1812(class_2561.method_30163((String)message));
    }

    public static Boolean inGame() {
        class_310 client = class_310.method_1551();
        return client.field_1724 != null && client.method_1562() != null;
    }
}

