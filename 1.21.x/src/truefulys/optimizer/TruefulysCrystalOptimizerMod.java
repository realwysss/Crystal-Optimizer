/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1511
 *  net.minecraft.class_1589
 *  net.minecraft.class_1621
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_238
 *  net.minecraft.class_239
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_310
 *  net.minecraft.class_3965
 *  net.minecraft.class_3966
 *  net.minecraft.class_640
 */
package truefulys.optimizer;

import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_1589;
import net.minecraft.class_1621;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_640;
import truefulys.optimizer.CrystalPlacementHelper;
import truefulys.optimizer.client.MinecraftClientAccess;
import truefulys.optimizer.command.EnableOptimizerCommand;
import truefulys.optimizer.network.OptimizerNetworking;

public class TruefulysCrystalOptimizerMod
implements ClientModInitializer {
    public static class_310 mc;
    public static int hitCount;
    public static int breakingBlockTick;
    private static class_1792 lastHeldItem;

    public void onInitializeClient() {
        mc = class_310.method_1551();
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
        if (TruefulysCrystalOptimizerMod.mc.field_1724 == null || TruefulysCrystalOptimizerMod.mc.field_1687 == null || TruefulysCrystalOptimizerMod.mc.field_1761 == null) {
            return;
        }
        class_1799 mainHandStack = TruefulysCrystalOptimizerMod.mc.field_1724.method_6047();
        class_1792 currentItem = mainHandStack.method_7909();
        if (lastHeldItem != currentItem) {
            hitCount = 0;
            breakingBlockTick = 0;
            if (currentItem == class_1802.field_8301) {
                ((MinecraftClientAccess)mc).truefulys$resetItemUseCooldown();
            }
            lastHeldItem = currentItem;
        }
        breakingBlockTick = TruefulysCrystalOptimizerMod.mc.field_1690.field_1886.method_1434() ? ++breakingBlockTick : 0;
        if (!TruefulysCrystalOptimizerMod.mc.field_1690.field_1904.method_1434()) {
            hitCount = 0;
        }
        if (breakingBlockTick > 2) {
            return;
        }
        if (hitCount < TruefulysCrystalOptimizerMod.limitPackets() && TruefulysCrystalOptimizerMod.lookingAtSaidEntity() && TruefulysCrystalOptimizerMod.mc.field_1690.field_1886.method_1434()) {
            class_1297 entity;
            if (hitCount >= 1 && (entity = TruefulysCrystalOptimizerMod.removeSaidEntity()) != null) {
                entity.method_31472();
            }
            ++hitCount;
        }
        if (!mainHandStack.method_31574(class_1802.field_8301) || !TruefulysCrystalOptimizerMod.mc.field_1690.field_1904.method_1434()) {
            return;
        }
        class_3965 placementTarget = CrystalPlacementHelper.getPlacementTarget(mc);
        if (placementTarget == null) {
            return;
        }
        ((MinecraftClientAccess)mc).truefulys$resetItemUseCooldown();
        TruefulysCrystalOptimizerMod.sendInteractBlockPacket(placementTarget.method_17777(), placementTarget.method_17780());
        if (TruefulysCrystalOptimizerMod.canPlaceCrystalServer(placementTarget.method_17777())) {
            TruefulysCrystalOptimizerMod.mc.field_1724.method_6104(TruefulysCrystalOptimizerMod.mc.field_1724.method_6058());
        }
    }

    private static class_1297 removeSaidEntity() {
        class_239 class_2392 = TruefulysCrystalOptimizerMod.mc.field_1765;
        if (!(class_2392 instanceof class_3966)) {
            return null;
        }
        class_3966 hit = (class_3966)class_2392;
        class_1297 entity = hit.method_17782();
        if (entity instanceof class_1511 || entity instanceof class_1621 || entity instanceof class_1589) {
            return entity;
        }
        return null;
    }

    private static boolean lookingAtSaidEntity() {
        class_3966 entity;
        class_239 class_2392 = TruefulysCrystalOptimizerMod.mc.field_1765;
        return class_2392 instanceof class_3966 && ((entity = (class_3966)class_2392).method_17782() instanceof class_1511 || entity.method_17782() instanceof class_1589 || entity.method_17782() instanceof class_1621);
    }

    private static class_1269 sendInteractBlockPacket(class_2338 pos, class_2350 dir) {
        class_243 vec = new class_243((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260());
        class_3965 result = new class_3965(vec, dir, pos, false);
        return TruefulysCrystalOptimizerMod.mc.field_1761.method_2896(TruefulysCrystalOptimizerMod.mc.field_1724, TruefulysCrystalOptimizerMod.mc.field_1724.method_6058(), result);
    }

    public static int limitPackets() {
        return TruefulysCrystalOptimizerMod.getPing() < 50 ? 1 : 2;
    }

    private static int getPing() {
        if (mc.method_1562() == null) {
            return 0;
        }
        class_640 playerListEntry = mc.method_1562().method_2871(TruefulysCrystalOptimizerMod.mc.field_1724.method_5667());
        if (playerListEntry == null) {
            return 0;
        }
        return playerListEntry.method_2959();
    }

    public static boolean canPlaceCrystalServer(class_2338 block) {
        class_2680 blockState = TruefulysCrystalOptimizerMod.mc.field_1687.method_8320(block);
        if (!CrystalPlacementHelper.isCrystalBase(blockState)) {
            return false;
        }
        class_2338 above = block.method_10084();
        if (!TruefulysCrystalOptimizerMod.mc.field_1687.method_22347(above)) {
            return false;
        }
        double x = above.method_10263();
        double y = above.method_10264();
        double z = above.method_10260();
        List entities = TruefulysCrystalOptimizerMod.mc.field_1687.method_8335(null, new class_238(x, y, z, x + 1.0, y + 2.0, z + 1.0));
        return entities.isEmpty();
    }
}

