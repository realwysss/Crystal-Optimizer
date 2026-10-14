/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package truefulys.optimizer.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import truefulys.optimizer.TruefulysCrystalOptimizerMod;
import truefulys.optimizer.client.ServerOptOutState;
import truefulys.optimizer.command.EnableOptimizerCommand;

@Mixin(value={LocalPlayer.class})
public abstract class ClientPlayerEntityMixin {
    @Inject(at={@At(value="INVOKE", target="Lnet/minecraft/client/player/AbstractClientPlayer;tick()V", ordinal=0)}, method={"tick()V"})
    private void useOwnTicks(CallbackInfo ci) {
        if (EnableOptimizerCommand.fastCrystal && !ServerOptOutState.isServerOptedOut()) {
            TruefulysCrystalOptimizerMod.useOwnTicks();
        }
    }
}

