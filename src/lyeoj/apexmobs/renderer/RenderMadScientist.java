package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityMadScientist;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMadScientist extends RenderLiving<EntityMadScientist> {
	
	private static final ResourceLocation SCIENTIST_TEXTURES = new ResourceLocation("apexmobs:textures/entity/madscientist.png");
	public static final Factory FACTORY = new Factory();
	
	public RenderMadScientist(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelVillager(0.0F), 0.5F);
	}

    public ModelVillager getMainModel()
    {
        return (ModelVillager)super.getMainModel();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMadScientist entity)
    {
        return SCIENTIST_TEXTURES;
    }
        
    public static class Factory implements IRenderFactory<EntityMadScientist> {
     	 
        @Override
        public Render<? super EntityMadScientist> createRenderFor(RenderManager manager) {
            return new RenderMadScientist(manager);
        }
    }

}
