/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.resources.Identifier
 */
package truefulys.optimizer.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OptimizerControlPayload(boolean allowed) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<OptimizerControlPayload> TYPE = new CustomPacketPayload.Type(Identifier.fromNamespaceAndPath((String)"truefulyscrystaloptimizer", (String)"optimizer_control"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OptimizerControlPayload> CODEC = StreamCodec.composite((StreamCodec)ByteBufCodecs.BOOL, OptimizerControlPayload::allowed, OptimizerControlPayload::new);

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

