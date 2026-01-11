package net.jonko0493.computercartographer;

import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jonko0493.computercartographer.block.ComputerizedCartographerBlock;
import net.jonko0493.computercartographer.block.ComputerizedCartographerBlockEntity;
import net.jonko0493.computercartographer.platform.BlockEntityTypeHelper;
import net.jonko0493.computercartographer.integration.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

public class ComputerCartographer
{
	public static final String MOD_ID = "computer_cartographer";
	public static List<IMapIntegration> integrations = new ArrayList<>();
	public static final Logger Log = LoggerFactory.getLogger(MOD_ID);
	public static void log(String message) {
		Log.info(message);
	}
	public static void logWarning(String message) {
		Log.warn(message);
	}
	public static void logException(Exception e) {
		Log.error(e.getMessage());
		Log.error(String.join("\n", Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)));
	}

	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
	public static Map<RegistrySupplier<? extends Block>, Item.Properties> BLOCK_ITEMS = new HashMap<>();
	public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create("computercraft", Registries.CREATIVE_MODE_TAB);

	// Helper to create block properties with the required block ID (needed in MC 1.21.x+)
	private static BlockBehaviour.Properties blockProps(String name) {
		Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, name);
		return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id));
	}

	// Helper to create item properties with the required item ID (needed in MC 1.21.x+)
	private static Item.Properties itemProps(String name) {
		Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, name);
		return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id));
	}

	public static RegistrySupplier<ComputerizedCartographerBlock> COMPUTERIZED_CARTOGRAPHER_BLOCK = registerBlockItem("computerized_cartographer", () -> new ComputerizedCartographerBlock(blockProps("computerized_cartographer").destroyTime(1.3f)));
	// Use platform-specific helper since BlockEntityType.Builder is private in vanilla 1.21.3+ (Fabric uses FabricBlockEntityTypeBuilder, NeoForge still has Builder)
	public static RegistrySupplier<BlockEntityType<ComputerizedCartographerBlockEntity>> COMPUTERIZED_CARTOGRAPHER_BLOCK_ENTITY = BLOCK_ENTITIES.register("computerized_cartographer_block_entity", () -> BlockEntityTypeHelper.create(ComputerizedCartographerBlockEntity::new, COMPUTERIZED_CARTOGRAPHER_BLOCK.get()));

	public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
		return BLOCKS.register(name, block);
	}

	public static <T extends Item> RegistrySupplier<T> registerItem(String name, Supplier<T> item) {
		return ITEMS.register(name, item);
	}

	public static <T extends Block> RegistrySupplier<T> registerBlockItem(String name, Supplier<T> block) {
		return registerBlockItem(name, block, new Item.Properties());
	}

	public static <T extends Block> RegistrySupplier<T> registerBlockItem(String name, Supplier<T> block, Item.Properties props) {
		RegistrySupplier<T> blockRegistered = registerBlock(name, block);
		BLOCK_ITEMS.put(blockRegistered, props);
		return blockRegistered;
	}

	public static void init() {
		initIntegrations();
		BLOCKS.register();
		BLOCK_ITEMS.forEach((block, itemprops) -> {
			Identifier blockId = block.getId();
			// Create item properties with proper ID set (required in MC 1.21.x+)
			Item.Properties propsWithId = itemProps(blockId.getPath());
			RegistrySupplier<BlockItem> blockItem = ITEMS.register(blockId, () -> new BlockItem(block.get(), propsWithId));
			CreativeTabRegistry.append(ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("computercraft", "tab")), blockItem);
		});
		ITEMS.register();
		BLOCK_ENTITIES.register();
	}

	public static void initIntegrations() {
		Log.atInfo().log("Attempting to add computer cartographer integrations...");
		integrations.clear();
		if (Platform.isModLoaded("bluemap")) {
			BlueMapIntegration blueMapIntegration = new BlueMapIntegration();
			if (blueMapIntegration.init()) {
				log("Registered BlueMap integration!");
				integrations.add(blueMapIntegration);
			}
		}
		// TODO: Re-enable when dependencies are available for 1.21.11
		// if (Platform.isModLoaded("dynmap")) {
		// 	DynmapIntegration dynmapIntegration = new DynmapIntegration();
		// 	if (dynmapIntegration.init()) {
		// 		log("Registered Dynmap integration!");
		// 		integrations.add(dynmapIntegration);
		// 	}
		// }
		// if (Platform.isModLoaded("pl3xmap")) {
		// 	Pl3xMapIntegration pl3xMapIntegration = new Pl3xMapIntegration();
		// 	if (pl3xMapIntegration.init()) {
		// 		log("Registered Pl3xMap integration!");
		// 		integrations.add(pl3xMapIntegration);
		// 	}
		// }
		// if (Platform.isModLoaded("squaremap")) {
		// 	SquaremapIntegration squaremapIntegration = new SquaremapIntegration();
		// 	if (squaremapIntegration.init()) {
		// 		log("Registered Squaremap integration!");
		// 		integrations.add(squaremapIntegration);
		// 	}
		// }
	}
}
