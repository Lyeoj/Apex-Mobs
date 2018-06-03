package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityTarZombie;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTarZombie extends RenderLiving<EntityTarZombie> {
	
	private static final ResourceLocation FIRE_ZOMBIE_TEXTURES = new ResourceLocation("apexmobs:textures/entity/firezombie.png");
    public static final Factory FACTORY = new Factory();

    public RenderTarZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelZombie(), 0.5F);
    }

    public ModelZombie getMainModel()
    {
        return (ModelZombie)super.getMainModel();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTarZombie entity)
    {
        return FIRE_ZOMBIE_TEXTURES;
    }
	
	public static class Factory implements IRenderFactory<EntityTarZombie> {
   	 
        @Override
        public Render<? super EntityTarZombie> createRenderFor(RenderManager manager) {
            return new RenderTarZombie(manager);
        }
    }

}
