/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package truefulys.optimizer.mixin;

import net.minecraft.class_1799;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1799.class})
public abstract class ItemStackMixin {
    @Inject(method={"method_7965()I"}, at={@At(value="HEAD")}, cancellable=true)
    private void stopBobbingAnimation(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue((Object)0);
    }
}

