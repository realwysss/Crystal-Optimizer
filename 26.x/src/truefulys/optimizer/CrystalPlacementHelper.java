/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 */
package truefulys.optimizer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public final class CrystalPlacementHelper {
    private static final Map<BlockPos, Long> PREDICTED_OBSIDIANS = new ConcurrentHashMap<BlockPos, Long>();

    private CrystalPlacementHelper() {
    }

    public static void addPredictedObsidian(BlockPos pos) {
        PREDICTED_OBSIDIANS.put(pos.immutable(), System.currentTimeMillis());
    }

    public static boolean isPredictedObsidian(BlockPos pos) {
        Long time = PREDICTED_OBSIDIANS.get(pos);
        if (time == null) {
            return false;
        }
        if (System.currentTimeMillis() - time > 1000L) {
            PREDICTED_OBSIDIANS.remove(pos);
            return false;
        }
        return true;
    }

    public static BlockHitResult getPlacementTarget(Minecraft client) {
        BlockHitResult blockHit;
        BlockHitResult target;
        if (client.player == null || client.level == null) {
            return null;
        }
        HitResult hitResult = client.hitResult;
        if (hitResult instanceof BlockHitResult && (target = CrystalPlacementHelper.resolveObsidianTarget(client, (blockHit = (BlockHitResult)hitResult).getBlockPos(), blockHit)) != null) {
            return target;
        }
        BlockHitResult rayHit = CrystalPlacementHelper.raycastLookPos(client);
        return CrystalPlacementHelper.resolveObsidianTarget(client, rayHit.getBlockPos(), rayHit);
    }

    private static BlockHitResult resolveObsidianTarget(Minecraft client, BlockPos pos, BlockHitResult source) {
        BlockState state = client.level.getBlockState(pos);
        if (CrystalPlacementHelper.isCrystalBase(state) || CrystalPlacementHelper.isPredictedObsidian(pos)) {
            return source;
        }
        BlockPos adjacent = pos.relative(source.getDirection());
        if (CrystalPlacementHelper.isPredictedObsidian(adjacent)) {
            return new BlockHitResult(Vec3.atCenterOf((Vec3i)adjacent).add(0.0, 0.5, 0.0), Direction.UP, adjacent, false);
        }
        BlockPos below = pos.below();
        BlockState belowState = client.level.getBlockState(below);
        if (CrystalPlacementHelper.isCrystalBase(belowState) || CrystalPlacementHelper.isPredictedObsidian(below)) {
            return new BlockHitResult(Vec3.atCenterOf((Vec3i)below).add(0.0, 0.5, 0.0), Direction.UP, below, false);
        }
        return null;
    }

    private static BlockHitResult raycastLookPos(Minecraft client) {
        Vec3 camPos = client.player.getEyePosition();
        Vec3 lookVec = CrystalPlacementHelper.lookVec(client);
        return client.level.clip(new ClipContext(camPos, camPos.add(lookVec.scale(4.5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, CollisionContext.of((Entity)client.player)));
    }

    public static Vec3 lookVec(Minecraft client) {
        float radians = (float)Math.PI / 180;
        float pi = (float)Math.PI;
        float yawCos = (float)Math.cos(-client.player.getYRot() * radians - pi);
        float yawSin = (float)Math.sin(-client.player.getYRot() * radians - pi);
        float pitchCos = (float)(-Math.cos(-client.player.getXRot() * radians));
        float pitchSin = (float)Math.sin(-client.player.getXRot() * radians);
        return new Vec3((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos)).normalize();
    }

    public static boolean isCrystalBase(BlockState state) {
        return state.is((Object)Blocks.OBSIDIAN) || state.is((Object)Blocks.BEDROCK);
    }
}

