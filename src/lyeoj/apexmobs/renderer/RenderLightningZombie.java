package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityLightningZombie;
import lyeoj.apexmobs.model.ModelLightningZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderLightningZombie extends RenderLiving<EntityLightningZombie> {
	
	private static final ResourceLocation LIGHTNING_ZOMBIE_TEXTURES = new ResourceLocation("apexmobs:textures/entity/lightningzombie.png");
    public static final Factory FACTORY = new Factory();

    public RenderLightningZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelLightningZombie(), 0.5F);
    }

    public ModelLightningZombie getMainModel()
    {
        return (ModelLightningZombie)super.getMainModel();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityLightningZombie entity)
    {
        return LIGHTNING_ZOMBIE_TEXTURES;
    }
	
	public static class Factory implements IRenderFactory<EntityLightningZombie> {
   	 
        @Override
        public Render<? super EntityLightningZombie> createRenderFor(RenderManager manager) {
            return new RenderLightningZombie(manager);
        }
    }

}
