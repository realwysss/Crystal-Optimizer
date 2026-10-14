/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2960
 *  net.minecraft.class_8710
 *  net.minecraft.class_8710$class_9154
 *  net.minecraft.class_9129
 *  net.minecraft.class_9135
 *  net.minecraft.class_9139
 */
package truefulys.optimizer.network;

import net.minecraft.class_2960;
import net.minecraft.class_8710;
import net.minecraft.class_9129;
import net.minecraft.class_9135;
import net.minecraft.class_9139;

public record OptimizerControlPayload(boolean allowed) implements class_8710
{
    public static final class_8710.class_9154<OptimizerControlPayload> ID = new class_8710.class_9154(class_2960.method_60655((String)"truefulyscrystaloptimizer", (String)"optimizer_control"));
    public static final class_9139<class_9129, OptimizerControlPayload> CODEC = class_9139.method_56434((class_9139)class_9135.field_48547, OptimizerControlPayload::allowed, OptimizerControlPayload::new);

    public class_8710.class_9154<? extends class_8710> method_56479() {
        return ID;
    }
}

