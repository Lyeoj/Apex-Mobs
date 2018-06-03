package lyeoj.apexmobs.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelBlaze - Either Mojang or a mod author
 * Created using Tabula 6.0.0
 */
public class ModelInferno extends ModelBase {
    public ModelRenderer rod11;
    public ModelRenderer rod10;
    public ModelRenderer rod1;
    public ModelRenderer rod2;
    public ModelRenderer rod12;
    public ModelRenderer head;
    public ModelRenderer rod7;
    public ModelRenderer rod8;
    public ModelRenderer rod9;
    public ModelRenderer rod3;
    public ModelRenderer rod4;
    public ModelRenderer rod5;
    public ModelRenderer rod6;
    public ModelRenderer rod11a;
    public ModelRenderer rod10a;
    public ModelRenderer rod1a;
    public ModelRenderer rod2a;
    public ModelRenderer rod12a;
    public ModelRenderer rod7a;
    public ModelRenderer rod8a;
    public ModelRenderer rod9a;
    public ModelRenderer rod3a;
    public ModelRenderer rod4a;
    public ModelRenderer rod5a;
    public ModelRenderer rod6a;
    private final ModelRenderer[] blazeSticks = new ModelRenderer[12];

    public ModelInferno() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.rod3 = new ModelRenderer(this, 0, 6);
        this.rod3.setRotationPoint(-9.0F, -1.46F, 0.0F);
        this.rod3.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod7 = new ModelRenderer(this, 40, 0);
        this.rod7.setRotationPoint(-4.95F, 1.01F, -4.95F);
        this.rod7.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod3a = new ModelRenderer(this, 0, 22);
        this.rod3a.setRotationPoint(15.0F, 4.0F, -2.0F);
        this.rod3a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.head = new ModelRenderer(this, 0, 6);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.rod9 = new ModelRenderer(this, 54, 4);
        this.rod9.setRotationPoint(4.46F, 11.96F, 2.27F);
        this.rod9.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod7a = new ModelRenderer(this, 32, 18);
        this.rod7a.setRotationPoint(10.0F, 4.0F, 10.0F);
        this.rod7a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod2a = new ModelRenderer(this, 54, 12);
        this.rod2a.setRotationPoint(-2.0F, 4.0F, -19.0F);
        this.rod2a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod8 = new ModelRenderer(this, 48, 0);
        this.rod8.setRotationPoint(4.95F, 1.06F, -4.95F);
        this.rod8.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod12a = new ModelRenderer(this, 46, 16);
        this.rod12a.setRotationPoint(0.0F, 4.0F, 7.1F);
        this.rod12a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod10 = new ModelRenderer(this, 8, 0);
        this.rod10.setRotationPoint(-2.27F, 11.89F, 4.46F);
        this.rod10.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod5 = new ModelRenderer(this, 32, 6);
        this.rod5.setRotationPoint(4.95F, 1.58F, 4.95F);
        this.rod5.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod5a = new ModelRenderer(this, 16, 22);
        this.rod5a.setRotationPoint(-10.0F, 4.0F, -10.0F);
        this.rod5a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod9a = new ModelRenderer(this, 40, 20);
        this.rod9a.setRotationPoint(-10.1F, 4.0F, 0.0F);
        this.rod9a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod6 = new ModelRenderer(this, 40, 6);
        this.rod6.setRotationPoint(-4.95F, 1.2F, 4.95F);
        this.rod6.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod11a = new ModelRenderer(this, 48, 8);
        this.rod11a.setRotationPoint(8.6F, 4.0F, 0.0F);
        this.rod11a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod8a = new ModelRenderer(this, 54, 18);
        this.rod8a.setRotationPoint(-12.0F, 4.0F, 8.0F);
        this.rod8a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod4a = new ModelRenderer(this, 8, 22);
        this.rod4a.setRotationPoint(0.0F, 4.0F, 16.0F);
        this.rod4a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod1 = new ModelRenderer(this, 16, 0);
        this.rod1.setRotationPoint(9.0F, -1.0F, 0.0F);
        this.rod1.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod2 = new ModelRenderer(this, 24, 0);
        this.rod2.setRotationPoint(0.0F, -1.12F, 9.0F);
        this.rod2.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod4 = new ModelRenderer(this, 24, 6);
        this.rod4.setRotationPoint(0.0F, -1.93F, -9.0F);
        this.rod4.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod6a = new ModelRenderer(this, 24, 22);
        this.rod6a.setRotationPoint(8.0F, 4.0F, -12.0F);
        this.rod6a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod1a = new ModelRenderer(this, 40, 12);
        this.rod1a.setRotationPoint(-16.3F, 4.0F, -2.4F);
        this.rod1a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod11 = new ModelRenderer(this, 0, 0);
        this.rod11.setRotationPoint(-4.46F, 11.35F, -2.27F);
        this.rod11.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod12 = new ModelRenderer(this, 32, 0);
        this.rod12.setRotationPoint(2.27F, 10.61F, -4.46F);
        this.rod12.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod10a = new ModelRenderer(this, 32, 12);
        this.rod10a.setRotationPoint(0.0F, 4.0F, -9.0F);
        this.rod10a.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.rod3.addChild(this.rod3a);
        this.rod7.addChild(this.rod7a);
        this.rod2.addChild(this.rod2a);
        this.rod12.addChild(this.rod12a);
        this.rod5.addChild(this.rod5a);
        this.rod9.addChild(this.rod9a);
        this.rod11.addChild(this.rod11a);
        this.rod8.addChild(this.rod8a);
        this.rod4.addChild(this.rod4a);
        this.rod6.addChild(this.rod6a);
        this.rod1.addChild(this.rod1a);
        this.rod10.addChild(this.rod10a);
        
        this.blazeSticks[0] = rod1;
        this.blazeSticks[1] = rod2;
        this.blazeSticks[2] = rod3;
        this.blazeSticks[3] = rod4;
        this.blazeSticks[4] = rod5;
        this.blazeSticks[5] = rod6;
        this.blazeSticks[6] = rod7;
        this.blazeSticks[7] = rod8;
        this.blazeSticks[8] = rod9;
        this.blazeSticks[9] = rod10;
        this.blazeSticks[10] = rod11;
        this.blazeSticks[11] = rod12;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.rod3.render(f5);
        this.rod7.render(f5);
        this.head.render(f5);
        this.rod9.render(f5);
        this.rod8.render(f5);
        this.rod10.render(f5);
        this.rod5.render(f5);
        this.rod6.render(f5);
        this.rod1.render(f5);
        this.rod2.render(f5);
        this.rod4.render(f5);
        this.rod11.render(f5);
        this.rod12.render(f5);
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        float f = ageInTicks * (float)Math.PI * -0.1F;

        for (int i = 0; i < 4; ++i)
        {
            this.blazeSticks[i].rotationPointY = -2.0F + MathHelper.cos(((float)(i * 2) + ageInTicks) * 0.25F);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(f) * 9.0F;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(f) * 9.0F;
            ++f;
        }

        f = ((float)Math.PI / 4F) + ageInTicks * (float)Math.PI * 0.03F;

        for (int j = 4; j < 8; ++j)
        {
            this.blazeSticks[j].rotationPointY = 2.0F + MathHelper.cos(((float)(j * 2) + ageInTicks) * 0.25F);
            this.blazeSticks[j].rotationPointX = MathHelper.cos(f) * 7.0F;
            this.blazeSticks[j].rotationPointZ = MathHelper.sin(f) * 7.0F;
            ++f;
        }

        f = 0.47123894F + ageInTicks * (float)Math.PI * -0.05F;

        for (int k = 8; k < 12; ++k)
        {
            this.blazeSticks[k].rotationPointY = 11.0F + MathHelper.cos(((float)k * 1.5F + ageInTicks) * 0.5F);
            this.blazeSticks[k].rotationPointX = MathHelper.cos(f) * 5.0F;
            this.blazeSticks[k].rotationPointZ = MathHelper.sin(f) * 5.0F;
            ++f;
        }

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
