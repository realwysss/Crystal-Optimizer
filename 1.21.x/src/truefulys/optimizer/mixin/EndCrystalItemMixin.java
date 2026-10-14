/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1269
 *  net.minecraft.class_1774
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1838
 *  net.minecraft.class_3965
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package truefulys.optimizer.mixin;

import net.minecraft.class_1269;
import net.minecraft.class_1774;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1838;
import net.minecraft.class_3965;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import truefulys.optimizer.CrystalPlacementHelper;
import truefulys.optimizer.TruefulysCrystalOptimizerMod;
import truefulys.optimizer.client.ServerOptOutState;

@Mixin(value={class_1774.class})
public class EndCrystalItemMixin {
    @Inject(method={"method_7884(Lnet/minecraft/class_1838;)Lnet/minecraft/class_1269;"}, at={@At(value="HEAD")}, cancellable=true)
    private void modifyDecrementAmount(class_1838 context, CallbackInfoReturnable<class_1269> cir) {
        if (ServerOptOutState.isServerOptedOut() || TruefulysCrystalOptimizerMod.mc.field_1724 == null || TruefulysCrystalOptimizerMod.mc.field_1687 == null) {
            return;
        }
        class_1799 mainHandStack = TruefulysCrystalOptimizerMod.mc.field_1724.method_6047();
        if (!mainHandStack.method_31574(class_1802.field_8301)) {
            return;
        }
        class_3965 placementTarget = CrystalPlacementHelper.getPlacementTarget(TruefulysCrystalOptimizerMod.mc);
        if (placementTarget == null) {
            return;
        }
        if (TruefulysCrystalOptimizerMod.canPlaceCrystalServer(placementTarget.method_17777())) {
            context.method_8041().method_7934(-1);
        }
    }
}

