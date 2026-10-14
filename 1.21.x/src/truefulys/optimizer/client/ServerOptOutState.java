/*
 * Decompiled with CFR 0.152.
 */
package truefulys.optimizer.client;

public final class ServerOptOutState {
    private static boolean onMultiplayer;
    private static boolean serverPolicyReceived;
    private static boolean serverAllowed;

    private ServerOptOutState() {
    }

    public static void reset() {
        onMultiplayer = false;
        serverPolicyReceived = false;
        serverAllowed = true;
    }

    public static void onJoinMultiplayer() {
        onMultiplayer = true;
    }

    public static void setServerAllowed(boolean allowed) {
        serverPolicyReceived = true;
        serverAllowed = allowed;
    }

    public static boolean isServerOptedOut() {
        return onMultiplayer && serverPolicyReceived && !serverAllowed;
    }

    static {
        serverAllowed = true;
    }
}

