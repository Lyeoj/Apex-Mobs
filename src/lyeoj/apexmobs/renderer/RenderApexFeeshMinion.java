package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.boss.EntityApexFeesh;
import lyeoj.apexmobs.entity.boss.EntityApexFeeshMinion;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderApexFeeshMinion extends RenderLiving<EntityApexFeeshMinion> {
	
	private static final ResourceLocation APEXFEESH_TEXTURE = new ResourceLocation("apexmobs:textures/entity/wanderfeesh.png");
    public static final Factory FACTORY = new Factory();
    private final float scale;

    public RenderApexFeeshMinion(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSilverfish(), 0.3F);
        this.scale = 1.3F;
    }

    protected float getDeathMaxRotation(EntityApexFeesh entityLivingBaseIn)
    {
        return 180.0F;
    }
    
    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityApexFeeshMinion entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityApexFeeshMinion entity)
    {
        return APEXFEESH_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityApexFeeshMinion> {
    	 
        @Override
        public Render<? super EntityApexFeeshMinion> createRenderFor(RenderManager manager) {
            return new RenderApexFeeshMinion(manager);
        }
    }

}
