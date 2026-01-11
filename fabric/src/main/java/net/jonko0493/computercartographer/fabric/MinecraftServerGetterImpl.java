package net.jonko0493.computercartographer.fabric;

import net.minecraft.server.MinecraftServer;

/**
 * Fabric implementation of MinecraftServerGetter.
 */
public class MinecraftServerGetterImpl {
    public static MinecraftServer getServerInstance() {
        return ComputerCartographerFabric.server;
    }
}
