/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2382
 *  net.minecraft.class_239
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_310
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 */
package truefulys.optimizer;

import net.minecraft.class_1297;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

public final class CrystalPlacementHelper {
    private CrystalPlacementHelper() {
    }

    public static class_3965 getPlacementTarget(class_310 client) {
        class_3965 blockHit;
        class_3965 target;
        if (client.field_1724 == null || client.field_1687 == null) {
            return null;
        }
        class_239 class_2392 = client.field_1765;
        if (class_2392 instanceof class_3965 && (target = CrystalPlacementHelper.resolveObsidianTarget(client, (blockHit = (class_3965)class_2392).method_17777(), blockHit)) != null) {
            return target;
        }
        class_3965 rayHit = CrystalPlacementHelper.raycastLookPos(client);
        return CrystalPlacementHelper.resolveObsidianTarget(client, rayHit.method_17777(), rayHit);
    }

    private static class_3965 resolveObsidianTarget(class_310 client, class_2338 pos, class_3965 source) {
        class_2680 state = client.field_1687.method_8320(pos);
        if (CrystalPlacementHelper.isCrystalBase(state)) {
            return source;
        }
        class_2338 below = pos.method_10074();
        class_2680 belowState = client.field_1687.method_8320(below);
        if (CrystalPlacementHelper.isCrystalBase(belowState)) {
            return new class_3965(class_243.method_24953((class_2382)below).method_1031(0.0, 0.5, 0.0), class_2350.field_11036, below, false);
        }
        return null;
    }

    private static class_3965 raycastLookPos(class_310 client) {
        class_243 camPos = client.field_1724.method_33571();
        class_243 lookVec = CrystalPlacementHelper.lookVec(client);
        return client.field_1687.method_17742(new class_3959(camPos, camPos.method_1019(lookVec.method_1021(4.5)), class_3959.class_3960.field_17559, class_3959.class_242.field_1348, (class_1297)client.field_1724));
    }

    public static class_243 lookVec(class_310 client) {
        float radians = (float)Math.PI / 180;
        float pi = (float)Math.PI;
        float yawCos = (float)Math.cos(-client.field_1724.method_36454() * radians - pi);
        float yawSin = (float)Math.sin(-client.field_1724.method_36454() * radians - pi);
        float pitchCos = (float)(-Math.cos(-client.field_1724.method_36455() * radians));
        float pitchSin = (float)Math.sin(-client.field_1724.method_36455() * radians);
        return new class_243((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos)).method_1029();
    }

    public static boolean isCrystalBase(class_2680 state) {
        return state.method_27852(class_2246.field_10540) || state.method_27852(class_2246.field_9987);
    }
}

