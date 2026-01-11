package net.jonko0493.computercartographer.platform;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * NeoForge implementation of BlockEntityTypeHelper.
 * In 1.21.11, BlockEntityType constructor is now public.
 */
public class BlockEntityTypeHelperImpl {
    
    public static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
        return new BlockEntityType<>(factory, blocks);
    }
}
