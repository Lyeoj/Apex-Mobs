package lyeoj.apexmobs.init;

import lyeoj.apexmobs.blocks.BlockMudLayer;
import lyeoj.apexmobs.blocks.BlockTarLayer;
import lyeoj.apexmobs.blocks.BlockTest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class AMBlocks {
	
	public static final Block TEST = new BlockTest(Material.GROUND);
	public static final Item TEST_BLOCK_ITEM = new ItemBlock(AMBlocks.TEST).setRegistryName(TEST.getRegistryName());
	public static final Block MUD_LAYER = new BlockMudLayer(Material.GROUND);
	public static final Item MUD_LAYER_ITEM = new ItemBlock(AMBlocks.MUD_LAYER).setRegistryName(MUD_LAYER.getRegistryName());
	public static final Block TAR_LAYER = new BlockTarLayer(Material.GROUND);
	public static final Item TAR_LAYER_ITEM = new ItemBlock(AMBlocks.TAR_LAYER).setRegistryName(TAR_LAYER.getRegistryName());
	
	public static final Block[] BLOCKLIST = {
			TEST,
			MUD_LAYER,
			TAR_LAYER
	};
}

