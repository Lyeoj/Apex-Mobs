package lyeoj.apexmobs.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTest extends Block{
	
	public BlockTest(Material mat) {
		super(mat);
		setUnlocalizedName("testblock");
		setRegistryName("testblock");
		setHardness(5f);
		setResistance(15f);
		setHarvestLevel("pickaxe", 1);
	}
}
