package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.EntityTest;
import lyeoj.apexmobs.model.ModelTestMob;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTestMob extends RenderLiving<EntityTest>
{
    private static final ResourceLocation TEST_TEXTURE = new ResourceLocation("apexmobs:textures/entity/bossfish.png");
    public static final Factory FACTORY = new Factory();

    public RenderTestMob(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelTestMob(), 0.3F);
    }

    protected float getDeathMaxRotation(EntitySilverfish entityLivingBaseIn)
    {
        return 180.0F;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTest entity)
    {
        return TEST_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityTest> {
    	 
        @Override
        public Render<? super EntityTest> createRenderFor(RenderManager manager) {
            return new RenderTestMob(manager);
        }
    }
}
