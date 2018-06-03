package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityFlinger;
import lyeoj.apexmobs.model.ModelFlinger;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFlinger extends RenderLiving<EntityFlinger>
{
    private static final ResourceLocation FLINGER_TEXTURES = new ResourceLocation("apexmobs:textures/entity/flinger.png");
    public static final Factory FACTORY = new Factory();

    public RenderFlinger(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelFlinger(0.0F), 0.5F);
    }

    public ModelFlinger getMainModel()
    {
        return (ModelFlinger)super.getMainModel();
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityFlinger entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        ((ModelFlinger)this.mainModel).holdingItem = !entity.getHeldItemMainhand().isEmpty();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityFlinger entity)
    {
        return FLINGER_TEXTURES;
    }

    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityWitch entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.9375F;
        GlStateManager.scale(f, f, f);
    }
	
	public static class Factory implements IRenderFactory<EntityFlinger> {
   	 
        @Override
        public Render<? super EntityFlinger> createRenderFor(RenderManager manager) {
            return new RenderFlinger(manager);
        }
    }
}
