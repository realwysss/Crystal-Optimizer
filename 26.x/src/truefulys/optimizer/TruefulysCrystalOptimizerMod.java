/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityTypes
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 */
package truefulys.optimizer;

import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import truefulys.optimizer.CrystalPlacementHelper;
import truefulys.optimizer.client.MinecraftClientAccess;
import truefulys.optimizer.command.EnableOptimizerCommand;
import truefulys.optimizer.network.OptimizerNetworking;

public class TruefulysCrystalOptimizerMod
implements ClientModInitializer {
    public static Minecraft mc;
    public static int hitCount;
    public static int breakingBlockTick;
    private static Item lastHeldItem;

    public void onInitializeClient() {
        mc = Minecraft.getInstance();
        OptimizerNetworking.registerPayloadType();
        OptimizerNetworking.registerClientHandlers();
        EnableOptimizerCommand command = new EnableOptimizerCommand();
        command.initializeToggleCommands();
    }

    public static void resetSessionState() {
        hitCount = 0;
        breakingBlockTick = 0;
        lastHeldItem = null;
    }

    public static void useOwnTicks() {
        if (TruefulysCrystalOptimizerMod.mc.player == null || TruefulysCrystalOptimizerMod.mc.level == null || TruefulysCrystalOptimizerMod.mc.gameMode == null) {
            return;
        }
        ItemStack mainHandStack = TruefulysCrystalOptimizerMod.mc.player.getMainHandItem();
        Item currentItem = mainHandStack.getItem();
        if (lastHeldItem != currentItem) {
            hitCount = 0;
            breakingBlockTick = 0;
            if (currentItem == Items.END_CRYSTAL) {
                ((MinecraftClientAccess)mc).truefulys$resetItemUseCooldown();
            }
            lastHeldItem = currentItem;
        }
        breakingBlockTick = TruefulysCrystalOptimizerMod.mc.options.keyAttack.isDown() ? ++breakingBlockTick : 0;
        if (!TruefulysCrystalOptimizerMod.mc.options.keyUse.isDown()) {
            hitCount = 0;
        }
        if (breakingBlockTick > 2) {
            return;
        }
        if (hitCount < TruefulysCrystalOptimizerMod.limitPackets() && TruefulysCrystalOptimizerMod.lookingAtSaidEntity() && TruefulysCrystalOptimizerMod.mc.options.keyAttack.isDown()) {
            Entity entity;
            if (hitCount >= 1 && (entity = TruefulysCrystalOptimizerMod.removeSaidEntity()) != null) {
                entity.discard();
            }
            ++hitCount;
        }
        if (!mainHandStack.is((Object)Items.END_CRYSTAL) || !TruefulysCrystalOptimizerMod.mc.options.keyUse.isDown()) {
            return;
        }
        BlockHitResult placementTarget = CrystalPlacementHelper.getPlacementTarget(mc);
        if (placementTarget == null) {
            return;
        }
        ((MinecraftClientAccess)mc).truefulys$resetItemUseCooldown();
        TruefulysCrystalOptimizerMod.sendInteractBlockPacket(placementTarget.getBlockPos(), placementTarget.getDirection());
        if (TruefulysCrystalOptimizerMod.canPlaceCrystalServer(placementTarget.getBlockPos())) {
            TruefulysCrystalOptimizerMod.mc.player.swing(TruefulysCrystalOptimizerMod.mc.player.getUsedItemHand());
        }
    }

    private static Entity removeSaidEntity() {
        HitResult hitResult = TruefulysCrystalOptimizerMod.mc.hitResult;
        if (!(hitResult instanceof EntityHitResult)) {
            return null;
        }
        EntityHitResult hit = (EntityHitResult)hitResult;
        Entity entity = hit.getEntity();
        if (entity.getType() == EntityTypes.END_CRYSTAL || entity.getType() == EntityTypes.SLIME || entity.getType() == EntityTypes.MAGMA_CUBE) {
            return entity;
        }
        return null;
    }

    private static boolean lookingAtSaidEntity() {
        EntityHitResult entity;
        HitResult hitResult = TruefulysCrystalOptimizerMod.mc.hitResult;
        return hitResult instanceof EntityHitResult && ((entity = (EntityHitResult)hitResult).getEntity().getType() == EntityTypes.END_CRYSTAL || entity.getEntity().getType() == EntityTypes.MAGMA_CUBE || entity.getEntity().getType() == EntityTypes.SLIME);
    }

    private static InteractionResult sendInteractBlockPacket(BlockPos pos, Direction dir) {
        Vec3 vec = new Vec3((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
        BlockHitResult result = new BlockHitResult(vec, dir, pos, false);
        return TruefulysCrystalOptimizerMod.mc.gameMode.useItemOn(TruefulysCrystalOptimizerMod.mc.player, TruefulysCrystalOptimizerMod.mc.player.getUsedItemHand(), result);
    }

    public static int limitPackets() {
        return TruefulysCrystalOptimizerMod.getPing() < 50 ? 1 : 2;
    }

    private static int getPing() {
        if (mc.getConnection() == null) {
            return 0;
        }
        PlayerInfo playerInfo = mc.getConnection().getPlayerInfo(TruefulysCrystalOptimizerMod.mc.player.getUUID());
        if (playerInfo == null) {
            return 0;
        }
        return playerInfo.getLatency();
    }

    public static boolean canPlaceCrystalServer(BlockPos block) {
        BlockState blockState = TruefulysCrystalOptimizerMod.mc.level.getBlockState(block);
        if (!CrystalPlacementHelper.isCrystalBase(blockState) && !CrystalPlacementHelper.isPredictedObsidian(block)) {
            return false;
        }
        BlockPos above = block.above();
        if (!TruefulysCrystalOptimizerMod.mc.level.isEmptyBlock(above)) {
            return false;
        }
        double x = above.getX();
        double y = above.getY();
        double z = above.getZ();
        List entities = TruefulysCrystalOptimizerMod.mc.level.getEntities((Entity)null, new AABB(x, y, z, x + 1.0, y + 2.0, z + 1.0));
        return entities.isEmpty();
    }
}

