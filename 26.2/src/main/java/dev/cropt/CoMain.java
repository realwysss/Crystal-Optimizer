package dev.cropt;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class CoMain implements ModInitializer {
	public void onInitialize() {
		PayloadTypeRegistry.clientboundPlay().register(CoPayload.T, CoPayload.C);
	}
}
