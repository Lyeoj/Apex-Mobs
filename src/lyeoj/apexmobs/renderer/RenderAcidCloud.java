package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.boss.EntityAcidCloud;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderAcidCloud extends Render<EntityAcidCloud> {

	public static final Factory FACTORY = new Factory();
	
	protected RenderAcidCloud(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAcidCloud entity) {
		return null;
	}
	
	public static class Factory implements IRenderFactory<EntityAcidCloud> {
	   	 
        @Override
        public Render<? super EntityAcidCloud> createRenderFor(RenderManager manager) {
            return new RenderAcidCloud(manager);
        }
    }

}
