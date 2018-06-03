package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.boss.EntityApexFeesh;
import lyeoj.apexmobs.model.ModelApexFeesh;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderApexFeesh extends RenderLiving<EntityApexFeesh> {
	
	private static final ResourceLocation APEXFEESH_TEXTURE = new ResourceLocation("apexmobs:textures/entity/apexfeesh.png");
    public static final Factory FACTORY = new Factory();
    private final float scale;

    public RenderApexFeesh(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelApexFeesh(), 0.3F);
        this.scale = 1.5F;
    }

    protected float getDeathMaxRotation(EntityApexFeesh entityLivingBaseIn)
    {
        return 180.0F;
    }
    
    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityApexFeesh entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityApexFeesh entity)
    {
        return APEXFEESH_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityApexFeesh> {
    	 
        @Override
        public Render<? super EntityApexFeesh> createRenderFor(RenderManager manager) {
            return new RenderApexFeesh(manager);
        }
    }

}
