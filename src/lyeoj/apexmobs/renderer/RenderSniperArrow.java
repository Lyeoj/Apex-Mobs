package lyeoj.apexmobs.renderer;

import lyeoj.apexmobs.entity.projectile.EntitySniperArrow;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSniperArrow extends RenderArrow<EntitySniperArrow> {
	
	public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");
    public static final ResourceLocation RES_TIPPED_ARROW = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");
    public static final Factory FACTORY = new Factory();

	public RenderSniperArrow(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySniperArrow entity) {
		return entity.getColor() > 0 ? RES_TIPPED_ARROW : RES_ARROW;
	}
	
	public static class Factory implements IRenderFactory<EntitySniperArrow> {
	   	 
        @Override
        public Render<? super EntitySniperArrow> createRenderFor(RenderManager manager) {
            return new RenderSniperArrow(manager);
        }
    }

}
