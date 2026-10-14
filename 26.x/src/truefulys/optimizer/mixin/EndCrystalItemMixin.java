/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.item.EndCrystalItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.phys.BlockHitResult
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package truefulys.optimizer.mixin;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.EndCrystalItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import truefulys.optimizer.CrystalPlacementHelper;
import truefulys.optimizer.TruefulysCrystalOptimizerMod;
import truefulys.optimizer.client.ServerOptOutState;

@Mixin(value={EndCrystalItem.class})
public class EndCrystalItemMixin {
    @Inject(method={"useOn"}, at={@At(value="HEAD")}, cancellable=true)
    private void modifyDecrementAmount(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (ServerOptOutState.isServerOptedOut() || TruefulysCrystalOptimizerMod.mc.player == null || TruefulysCrystalOptimizerMod.mc.level == null) {
            return;
        }
        ItemStack mainHandStack = TruefulysCrystalOptimizerMod.mc.player.getMainHandItem();
        if (!mainHandStack.is((Object)Items.END_CRYSTAL)) {
            return;
        }
        BlockHitResult placementTarget = CrystalPlacementHelper.getPlacementTarget(TruefulysCrystalOptimizerMod.mc);
        if (placementTarget == null) {
            return;
        }
        if (TruefulysCrystalOptimizerMod.canPlaceCrystalServer(placementTarget.getBlockPos())) {
            context.getItemInHand().shrink(-1);
        }
    }
}

