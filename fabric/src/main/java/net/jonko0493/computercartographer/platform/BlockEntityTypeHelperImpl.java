package net.jonko0493.computercartographer.platform;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Fabric implementation of BlockEntityTypeHelper.
 * Uses FabricBlockEntityTypeBuilder since BlockEntityType.Builder is private in 1.21.3+.
 */
public class BlockEntityTypeHelperImpl {
    
    public static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory::create, blocks).build();
    }
}
