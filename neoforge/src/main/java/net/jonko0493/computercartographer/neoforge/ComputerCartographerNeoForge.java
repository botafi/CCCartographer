package net.jonko0493.computercartographer.neoforge;

import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.jonko0493.computercartographer.ComputerCartographer;
import net.jonko0493.computercartographer.block.ComputerizedCartographerBlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Mod(ComputerCartographer.MOD_ID)
public class ComputerCartographerNeoForge {
    public ComputerCartographerNeoForge(IEventBus modBus) {
        // Initialize the mod
        ComputerCartographer.init();

        // Register capabilities for CC:Tweaked peripheral
        modBus.addListener(this::registerCapabilities);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
            PeripheralCapability.get(),
            ComputerCartographer.COMPUTERIZED_CARTOGRAPHER_BLOCK_ENTITY.get(),
            (blockEntity, direction) -> blockEntity.getPeripheral()
        );
    }
}
