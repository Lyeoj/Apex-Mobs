package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntitySniperSkeleton;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSniperSkeleton extends RenderSkeleton {

	private static final ResourceLocation SNIPER_SKELETON_TEXTURES = new ResourceLocation("apexmobs:textures/entity/sniperskeleton.png");
	public static final Factory FACTORY = new Factory();
	
	public RenderSniperSkeleton(RenderManager p_i47191_1_) {
		super(p_i47191_1_);
        this.addLayer(new LayerSniperClothing(this));
	}
	
	/**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractSkeleton entity)
    {
        return SNIPER_SKELETON_TEXTURES;
    }
    
    public static class Factory implements IRenderFactory<EntitySniperSkeleton> {
      	 
        @Override
        public Render<? super EntitySniperSkeleton> createRenderFor(RenderManager manager) {
            return new RenderSniperSkeleton(manager);
        }
    }

}
