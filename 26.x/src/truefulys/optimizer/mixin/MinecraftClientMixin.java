/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package truefulys.optimizer.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import truefulys.optimizer.TruefulysCrystalOptimizerMod;
import truefulys.optimizer.client.MinecraftClientAccess;
import truefulys.optimizer.client.ServerOptOutState;
import truefulys.optimizer.command.EnableOptimizerCommand;

@Mixin(value={Minecraft.class})
public abstract class MinecraftClientMixin
implements MinecraftClientAccess {
    @Shadow
    private int rightClickDelay;

    @Override
    public void truefulys$resetItemUseCooldown() {
        this.rightClickDelay = 0;
    }

    @Inject(at={@At(value="HEAD")}, method={"startUseItem"}, cancellable=true)
    private void onDoItemUse(CallbackInfo ci) {
        ItemStack mainHand;
        if (EnableOptimizerCommand.fastCrystal && !ServerOptOutState.isServerOptedOut() && (mainHand = TruefulysCrystalOptimizerMod.mc.player.getMainHandItem()).is((Object)Items.END_CRYSTAL)) {
            ci.cancel();
        }
    }
}

