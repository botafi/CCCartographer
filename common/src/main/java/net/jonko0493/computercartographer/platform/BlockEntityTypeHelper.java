package net.jonko0493.computercartographer.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Platform-specific helper for creating BlockEntityType instances.
 * In MC 1.21.3+, BlockEntityType.Builder was made private, so each platform
 * needs its own implementation:
 * - Fabric: Uses FabricBlockEntityTypeBuilder
 * - NeoForge: Uses BlockEntityType.Builder (still public in NeoForge)
 */
public class BlockEntityTypeHelper {
    
    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
        throw new AssertionError("Platform implementation not found!");
    }
}
