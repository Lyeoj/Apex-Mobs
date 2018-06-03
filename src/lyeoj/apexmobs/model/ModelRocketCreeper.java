package lyeoj.apexmobs.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelRocketCreeper - Either Mojang or a mod author
 * Created using Tabula 6.0.0
 */
public class ModelRocketCreeper extends ModelBase {
    public ModelRenderer creeperBody;
    public ModelRenderer leg2;
    public ModelRenderer leg4;
    public ModelRenderer leg3;
    public ModelRenderer creeperHead;
    public ModelRenderer creeperHat;
    public ModelRenderer leg1;
    public ModelRenderer jetpack1;
    public ModelRenderer jetpack2;
    public ModelRenderer jetpack3;

    public ModelRocketCreeper() {
        this.textureWidth = 64;
        this.textureHeight = 48;
        this.creeperBody = new ModelRenderer(this, 0, 0);
        this.creeperBody.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.creeperBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.jetpack2 = new ModelRenderer(this, 0, 32);
        this.jetpack2.setRotationPoint(-4.2F, 2.0F, 0.1F);
        this.jetpack2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 4, 0.0F);
        this.jetpack1 = new ModelRenderer(this, 48, 20);
        this.jetpack1.setRotationPoint(1.2F, 2.0F, 0.1F);
        this.jetpack1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 4, 0.0F);
        this.leg2 = new ModelRenderer(this, 24, 0);
        this.leg2.setRotationPoint(2.0F, 18.0F, 4.0F);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.leg4 = new ModelRenderer(this, 40, 0);
        this.leg4.setRotationPoint(2.0F, 18.0F, -4.0F);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.leg3 = new ModelRenderer(this, 24, 10);
        this.leg3.setRotationPoint(-2.0F, 18.0F, -4.0F);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.creeperHat = new ModelRenderer(this, 24, 24);
        this.creeperHat.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.creeperHat.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.creeperHead = new ModelRenderer(this, 0, 16);
        this.creeperHead.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.creeperHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.leg1 = new ModelRenderer(this, 40, 10);
        this.leg1.setRotationPoint(-2.0F, 18.0F, 4.0F);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.jetpack3 = new ModelRenderer(this, 10, 39);
        this.jetpack3.mirror = true;
        this.jetpack3.setRotationPoint(-2.0F, 4.0F, 0.0F);
        this.jetpack3.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4, 0.0F);
        this.creeperBody.addChild(this.jetpack2);
        this.creeperBody.addChild(this.jetpack1);
        this.creeperBody.addChild(this.jetpack3);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.creeperBody.render(f5);
        this.leg2.render(f5);
        this.leg4.render(f5);
        this.leg3.render(f5);
        this.creeperHat.render(f5);
        this.creeperHead.render(f5);
        this.leg1.render(f5);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.creeperHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.creeperHead.rotateAngleX = headPitch * 0.017453292F;
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
