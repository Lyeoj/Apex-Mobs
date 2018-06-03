package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityWanderingFeesh;
import lyeoj.apexmobs.model.ModelWanderingFeesh;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderWanderingFeesh extends RenderLiving<EntityWanderingFeesh>{
	private static final ResourceLocation WANDERFEESH_TEXTURE = new ResourceLocation("apexmobs:textures/entity/wanderfeesh.png");
    public static final Factory FACTORY = new Factory();
    private final float scale;

    public RenderWanderingFeesh(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelWanderingFeesh(), 0.3F);
        this.scale = 1.3F;
    }

    protected float getDeathMaxRotation(EntityWanderingFeesh entityLivingBaseIn)
    {
        return 180.0F;
    }
    
    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityWanderingFeesh entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityWanderingFeesh entity)
    {
        return WANDERFEESH_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityWanderingFeesh> {
    	 
        @Override
        public Render<? super EntityWanderingFeesh> createRenderFor(RenderManager manager) {
            return new RenderWanderingFeesh(manager);
        }
    }
}

