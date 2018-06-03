package lyeoj.apexmobs.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelSilverfish - Either Mojang or a mod author
 * Created using Tabula 6.0.0
 */
public class ModelApexFeesh extends ModelBase {
	private final ModelRenderer[] silverfishBodyParts = new ModelRenderer[7];
    private final ModelRenderer[] silverfishWings = new ModelRenderer[3];
    public ModelRenderer silverfishBody3;
    public ModelRenderer silverfishWings3;
    public ModelRenderer silverfishBody4;
    public ModelRenderer silverfishBody5;
    public ModelRenderer silverfishWings1;
    public ModelRenderer silverfishBody6;
    public ModelRenderer silverfishWings2;
    public ModelRenderer silverfishBody1;
    public ModelRenderer silverfishBody2;
    public ModelRenderer silverfishBody7;
    public ModelRenderer shape11;
    public ModelRenderer shape12;
    public ModelRenderer shape13;
    public ModelRenderer shape16;
    public ModelRenderer shape17;
    public ModelRenderer shape18;
    public ModelRenderer shape14;
    public ModelRenderer shape15;

    public ModelApexFeesh() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.silverfishBody1 = new ModelRenderer(this, 18, 7);
        this.silverfishBody1.setRotationPoint(0.0F, 22.0F, -3.5F);
        this.silverfishBody1.addBox(-1.5F, 0.0F, -1.0F, 3, 2, 2, 0.0F);
        this.setRotateAngle(silverfishBody1, 0.0F, 0.47123889803846897F, 0.0F);
        this.shape12 = new ModelRenderer(this, 59, 7);
        this.shape12.setRotationPoint(-2.1F, -1.8F, -0.3F);
        this.shape12.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(shape12, -0.36425021489121656F, 0.0F, -0.045553093477052F);
        this.shape14 = new ModelRenderer(this, 0, 16);
        this.shape14.setRotationPoint(0.2F, 0.4F, -0.5F);
        this.shape14.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(shape14, -0.045553093477052F, 0.0F, 0.091106186954104F);
        this.shape11 = new ModelRenderer(this, 56, 3);
        this.shape11.setRotationPoint(3.3F, -1.2F, 0.0F);
        this.shape11.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(shape11, 0.0F, 0.0F, 0.6829473363053812F);
        this.silverfishWings2 = new ModelRenderer(this, 0, 7);
        this.silverfishWings2.setRotationPoint(1.2F, 20.0F, 7.0F);
        this.silverfishWings2.addBox(-3.0F, 0.0F, -1.5F, 6, 4, 3, 0.0F);
        this.setRotateAngle(silverfishWings2, 0.0F, -0.14556045961632708F, 0.0F);
        this.silverfishBody3 = new ModelRenderer(this, 0, 0);
        this.silverfishBody3.setRotationPoint(0.0F, 20.0F, 1.0F);
        this.silverfishBody3.addBox(-3.0F, 0.0F, -1.5F, 6, 4, 3, 0.0F);
        this.setRotateAngle(silverfishBody3, 0.0F, 0.09232791743050003F, 0.0F);
        this.shape15 = new ModelRenderer(this, 14, 14);
        this.shape15.setRotationPoint(-0.9F, -0.6F, -0.4F);
        this.shape15.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.silverfishBody5 = new ModelRenderer(this, 46, 0);
        this.silverfishBody5.setRotationPoint(1.2F, 22.0F, 7.0F);
        this.silverfishBody5.addBox(-1.0F, 0.0F, -1.5F, 2, 2, 3, 0.0F);
        this.setRotateAngle(silverfishBody5, 0.0F, -0.14556045961632708F, 0.0F);
        this.shape18 = new ModelRenderer(this, 8, 14);
        this.shape18.setRotationPoint(-1.6F, -0.4F, 0.0F);
        this.shape18.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(shape18, 0.0F, 0.0F, 0.36425021489121656F);
        this.silverfishWings1 = new ModelRenderer(this, 31, 6);
        this.silverfishWings1.setRotationPoint(0.0F, 16.0F, 1.0F);
        this.silverfishWings1.addBox(-5.0F, 0.0F, -1.5F, 10, 8, 3, 0.0F);
        this.setRotateAngle(silverfishWings1, 0.0F, 0.09232791743050003F, 0.0F);
        this.silverfishBody2 = new ModelRenderer(this, 18, 11);
        this.silverfishBody2.setRotationPoint(0.29F, 21.0F, -1.5F);
        this.silverfishBody2.addBox(-2.0F, 0.0F, -1.0F, 4, 3, 2, 0.0F);
        this.setRotateAngle(silverfishBody2, 0.0F, 0.27995081201989047F, 0.0F);
        this.shape16 = new ModelRenderer(this, 57, 10);
        this.shape16.setRotationPoint(0.0F, -1.9F, 0.8F);
        this.shape16.addBox(0.1F, 0.0F, 0.6F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape16, -0.5009094953223726F, 0.0F, 0.0F);
        this.silverfishWings3 = new ModelRenderer(this, 18, 0);
        this.silverfishWings3.setRotationPoint(0.29F, 19.0F, -1.5F);
        this.silverfishWings3.addBox(-3.0F, 0.0F, -1.5F, 6, 5, 2, 0.0F);
        this.setRotateAngle(silverfishWings3, 0.0F, 0.27995081201989047F, 0.0F);
        this.silverfishBody6 = new ModelRenderer(this, 53, 0);
        this.silverfishBody6.setRotationPoint(1.33F, 23.0F, 9.5F);
        this.silverfishBody6.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
        this.setRotateAngle(silverfishBody6, 0.0F, -0.44436082755775635F, 0.0F);
        this.shape13 = new ModelRenderer(this, 26, 7);
        this.shape13.setRotationPoint(-4.5F, 1.5F, 0.2F);
        this.shape13.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(shape13, 0.136659280431156F, 0.0F, 0.136659280431156F);
        this.silverfishBody4 = new ModelRenderer(this, 34, 0);
        this.silverfishBody4.setRotationPoint(0.62F, 21.0F, 4.0F);
        this.silverfishBody4.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
        this.setRotateAngle(silverfishBody4, 0.0F, 0.04921828490624009F, 0.0F);
        this.shape17 = new ModelRenderer(this, 0, 14);
        this.shape17.setRotationPoint(0.0F, 1.0F, -0.6F);
        this.shape17.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(shape17, 0.0F, 0.0F, -0.11903145498601327F);
        this.silverfishBody7 = new ModelRenderer(this, 13, 4);
        this.silverfishBody7.setRotationPoint(0.78F, 23.0F, 11.5F);
        this.silverfishBody7.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(silverfishBody7, 0.0F, -0.747000919853573F, 0.0F);
        this.silverfishBody3.addChild(this.shape12);
        this.silverfishBody2.addChild(this.shape14);
        this.silverfishBody3.addChild(this.shape11);
        this.silverfishBody2.addChild(this.shape15);
        this.silverfishBody5.addChild(this.shape18);
        this.silverfishBody4.addChild(this.shape16);
        this.silverfishBody3.addChild(this.shape13);
        this.silverfishBody4.addChild(this.shape17);
        
        this.silverfishBodyParts[0] = silverfishBody1;
        this.silverfishBodyParts[1] = silverfishBody2;
        this.silverfishBodyParts[2] = silverfishBody3;
        this.silverfishBodyParts[3] = silverfishBody4;
        this.silverfishBodyParts[4] = silverfishBody5;
        this.silverfishBodyParts[5] = silverfishBody6;
        this.silverfishBodyParts[6] = silverfishBody7;
        
        this.silverfishWings[0] = silverfishWings1;
        this.silverfishWings[1] = silverfishWings2;
        this.silverfishWings[2] = silverfishWings3;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.silverfishBody1.render(f5);
        this.silverfishWings2.render(f5);
        this.silverfishBody3.render(f5);
        this.silverfishBody5.render(f5);
        this.silverfishWings1.render(f5);
        this.silverfishBody2.render(f5);
        this.silverfishWings3.render(f5);
        this.silverfishBody6.render(f5);
        this.silverfishBody4.render(f5);
        this.silverfishBody7.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        for (int i = 0; i < this.silverfishBodyParts.length; ++i)
        {
            this.silverfishBodyParts[i].rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (float)(1 + Math.abs(i - 2));
            this.silverfishBodyParts[i].rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * (float)Math.abs(i - 2);
        }

        this.silverfishWings[0].rotateAngleY = this.silverfishBodyParts[2].rotateAngleY;
        this.silverfishWings[1].rotateAngleY = this.silverfishBodyParts[4].rotateAngleY;
        this.silverfishWings[1].rotationPointX = this.silverfishBodyParts[4].rotationPointX;
        this.silverfishWings[2].rotateAngleY = this.silverfishBodyParts[1].rotateAngleY;
        this.silverfishWings[2].rotationPointX = this.silverfishBodyParts[1].rotationPointX;
    }
    
}
