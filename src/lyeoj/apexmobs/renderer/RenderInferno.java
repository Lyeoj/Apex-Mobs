package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityInferno;
import lyeoj.apexmobs.model.ModelInferno;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderInferno extends RenderLiving<EntityInferno> {
	
	private static final ResourceLocation BLAZE_TEXTURES = new ResourceLocation("apexmobs:textures/entity/inferno.png");
	public static final Factory FACTORY = new Factory();

    public RenderInferno(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelInferno(), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityInferno entity)
    {
        return BLAZE_TEXTURES;
    }
    
    public static class Factory implements IRenderFactory<EntityInferno> {
     	 
        @Override
        public Render<? super EntityInferno> createRenderFor(RenderManager manager) {
            return new RenderInferno(manager);
        }
    }

}
