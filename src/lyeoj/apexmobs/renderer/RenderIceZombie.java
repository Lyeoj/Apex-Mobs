package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityIceZombie;
import lyeoj.apexmobs.model.ModelIceZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderIceZombie extends RenderLiving<EntityIceZombie> {
	
	private static final ResourceLocation ICE_ZOMBIE_TEXTURES = new ResourceLocation("apexmobs:textures/entity/icezombie.png");
    public static final Factory FACTORY = new Factory();

    public RenderIceZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelIceZombie(), 0.5F);
    }

    public ModelIceZombie getMainModel()
    {
        return (ModelIceZombie)super.getMainModel();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityIceZombie entity)
    {
        return ICE_ZOMBIE_TEXTURES;
    }
	
	public static class Factory implements IRenderFactory<EntityIceZombie> {
   	 
        @Override
        public Render<? super EntityIceZombie> createRenderFor(RenderManager manager) {
            return new RenderIceZombie(manager);
        }
    }

}
