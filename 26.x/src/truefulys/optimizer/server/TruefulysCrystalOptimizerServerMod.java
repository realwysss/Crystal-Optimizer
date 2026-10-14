/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.DedicatedServerModInitializer
 */
package truefulys.optimizer.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import truefulys.optimizer.network.OptimizerNetworking;
import truefulys.optimizer.server.ServerOptOutConfig;

public class TruefulysCrystalOptimizerServerMod
implements DedicatedServerModInitializer {
    public void onInitializeServer() {
        OptimizerNetworking.registerPayloadType();
        ServerOptOutConfig.load();
        OptimizerNetworking.registerServerHandlers();
    }
}

