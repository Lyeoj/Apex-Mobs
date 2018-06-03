package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityShadow;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShadow extends RenderBiped<EntityShadow>
{
    private static final ResourceLocation SHADOW_TEXTURES = new ResourceLocation("apexmobs:textures/entity/shadow.png");
    public static final Factory FACTORY = new Factory();

    public RenderShadow(RenderManager renderManagerIn)
    {
    	super(renderManagerIn, new ModelPlayer(0.0F, false), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityShadow entity)
    {
        return SHADOW_TEXTURES;
    }
    
    public static class Factory implements IRenderFactory<EntityShadow> {
      	 
        @Override
        public Render<? super EntityShadow> createRenderFor(RenderManager manager) {
            return new RenderShadow(manager);
        }
    }
    
    
}
