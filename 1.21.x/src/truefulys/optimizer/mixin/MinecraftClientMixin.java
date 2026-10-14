/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_310
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package truefulys.optimizer.mixin;

import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import truefulys.optimizer.TruefulysCrystalOptimizerMod;
import truefulys.optimizer.client.MinecraftClientAccess;
import truefulys.optimizer.client.ServerOptOutState;
import truefulys.optimizer.command.EnableOptimizerCommand;

@Mixin(value={class_310.class})
public abstract class MinecraftClientMixin
implements MinecraftClientAccess {
    @Shadow
    private int field_1752;

    @Override
    public void truefulys$resetItemUseCooldown() {
        this.field_1752 = 0;
    }

    @Inject(at={@At(value="HEAD")}, method={"method_1583()V"}, cancellable=true)
    private void onDoItemUse(CallbackInfo ci) {
        class_1799 mainHand;
        if (EnableOptimizerCommand.fastCrystal && !ServerOptOutState.isServerOptedOut() && (mainHand = TruefulysCrystalOptimizerMod.mc.field_1724.method_6047()).method_31574(class_1802.field_8301)) {
            ci.cancel();
        }
    }
}

