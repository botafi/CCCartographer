package net.jonko0493.computercartographer;

import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/**
 * NeoForge implementation of MinecraftServerGetter.
 */
public class MinecraftServerGetterImpl {
    public static MinecraftServer getServerInstance() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
