package lyeoj.apexmobs.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * ModelLightningZombie - Lyeoj
 * Created using Tabula 6.0.0
 */
public class ModelLightningZombie extends ModelBase {
    public ModelRenderer rightArm;
    public ModelRenderer rightLeg;
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leftArm;
    public ModelRenderer leftLeg;
    public ModelRenderer shape8;
    public ModelRenderer shape10;
    public ModelRenderer shape20;
    public ModelRenderer shape22;
    public ModelRenderer shape9;
    public ModelRenderer shape11;
    public ModelRenderer shape12;
    public ModelRenderer shape13;
    public ModelRenderer shape14;
    public ModelRenderer shape15;
    public ModelRenderer shape16;
    public ModelRenderer shape17;
    public ModelRenderer shape18;
    public ModelRenderer shape19;
    public ModelRenderer shape21;
    public ModelRenderer shape23;
    
    public ModelBiped.ArmPose leftArmPose;
    public ModelBiped.ArmPose rightArmPose;
    
    public boolean isSneak;

    public ModelLightningZombie() {
    	this.leftArmPose = ModelBiped.ArmPose.EMPTY;
        this.rightArmPose = ModelBiped.ArmPose.EMPTY;
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shape20 = new ModelRenderer(this, 12, 0);
        this.shape20.setRotationPoint(-6.0F, -4.5F, -0.5F);
        this.shape20.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shape10 = new ModelRenderer(this, 32, 0);
        this.shape10.setRotationPoint(-2.0F, -9.0F, -2.0F);
        this.shape10.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, 0.0F);
        this.shape8 = new ModelRenderer(this, 48, 0);
        this.shape8.setRotationPoint(-1.0F, -20.0F, -1.0F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.head = new ModelRenderer(this, 24, 8);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.rightLeg = new ModelRenderer(this, 16, 0);
        this.rightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 0);
        this.rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(rightArm, -1.3962634015954636F, -0.10000736613927509F, 0.10000736613927509F);
        this.shape17 = new ModelRenderer(this, 36, 24);
        this.shape17.setRotationPoint(0.0F, 4.0F, 2.0F);
        this.shape17.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
        this.shape19 = new ModelRenderer(this, 56, 3);
        this.shape19.setRotationPoint(2.0F, 2.0F, 0.0F);
        this.shape19.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.shape23 = new ModelRenderer(this, 0, 40);
        this.shape23.setRotationPoint(2.0F, -0.5F, -0.5F);
        this.shape23.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.shape11 = new ModelRenderer(this, 0, 32);
        this.shape11.setRotationPoint(2.0F, 10.0F, 0.0F);
        this.shape11.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
        this.shape14 = new ModelRenderer(this, 52, 24);
        this.shape14.setRotationPoint(2.0F, 6.0F, 0.0F);
        this.shape14.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.body = new ModelRenderer(this, 0, 16);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.shape15 = new ModelRenderer(this, 12, 37);
        this.shape15.setRotationPoint(-3.0F, 6.0F, 0.0F);
        this.shape15.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.shape22 = new ModelRenderer(this, 28, 0);
        this.shape22.setRotationPoint(4.0F, -4.5F, -0.5F);
        this.shape22.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shape16 = new ModelRenderer(this, 54, 12);
        this.shape16.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.shape16.addBox(0.0F, 0.0F, -2.0F, 2, 1, 2, 0.0F);
        this.leftArm = new ModelRenderer(this, 24, 24);
        this.leftArm.mirror = true;
        this.leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(leftArm, -1.3962634015954636F, 0.10000736613927509F, -0.10000736613927509F);
        this.shape13 = new ModelRenderer(this, 0, 35);
        this.shape13.setRotationPoint(0.0F, 8.0F, -4.0F);
        this.shape13.addBox(0.0F, 0.0F, 0.0F, 2, 1, 4, 0.0F);
        this.shape21 = new ModelRenderer(this, 54, 38);
        this.shape21.setRotationPoint(-2.0F, -0.5F, -0.5F);
        this.shape21.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.shape9 = new ModelRenderer(this, 32, 5);
        this.shape9.setRotationPoint(-5.0F, 10.0F, 0.0F);
        this.shape9.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
        this.leftLeg = new ModelRenderer(this, 40, 24);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.shape12 = new ModelRenderer(this, 10, 32);
        this.shape12.setRotationPoint(0.0F, 8.0F, 2.0F);
        this.shape12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 4, 0.0F);
        this.shape18 = new ModelRenderer(this, 56, 0);
        this.shape18.setRotationPoint(-1.0F, 2.0F, 0.0F);
        this.shape18.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.head.addChild(this.shape20);
        this.head.addChild(this.shape10);
        this.head.addChild(this.shape8);
        this.shape8.addChild(this.shape17);
        this.shape8.addChild(this.shape19);
        this.shape22.addChild(this.shape23);
        this.shape8.addChild(this.shape11);
        this.shape8.addChild(this.shape14);
        this.shape8.addChild(this.shape15);
        this.head.addChild(this.shape22);
        this.shape8.addChild(this.shape16);
        this.shape8.addChild(this.shape13);
        this.shape20.addChild(this.shape21);
        this.shape8.addChild(this.shape9);
        this.shape8.addChild(this.shape12);
        this.shape8.addChild(this.shape18);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.head.render(f5);
        this.rightLeg.render(f5);
        this.rightArm.render(f5);
        this.body.render(f5);
        this.leftArm.render(f5);
        this.leftLeg.render(f5);
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
    @SuppressWarnings("incomplete-switch")
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        if (flag)
        {
            this.head.rotateAngleX = -((float)Math.PI / 4F);
        }
        else
        {
            this.head.rotateAngleX = headPitch * 0.017453292F;
        }

        this.body.rotateAngleY = 0.0F;
        this.rightArm.rotationPointZ = 0.0F;
        this.rightArm.rotationPointX = -5.0F;
        this.leftArm.rotationPointZ = 0.0F;
        this.leftArm.rotationPointX = 5.0F;
        float f = 1.0F;

        if (flag)
        {
            f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F)
        {
            f = 1.0F;
        }

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
        this.rightLeg.rotateAngleZ = 0.0F;
        this.leftLeg.rotateAngleZ = 0.0F;

        if (this.isRiding)
        {
            this.rightArm.rotateAngleX += -((float)Math.PI / 5F);
            this.leftArm.rotateAngleX += -((float)Math.PI / 5F);
            this.rightLeg.rotateAngleX = -1.4137167F;
            this.rightLeg.rotateAngleY = ((float)Math.PI / 10F);
            this.rightLeg.rotateAngleZ = 0.07853982F;
            this.leftLeg.rotateAngleX = -1.4137167F;
            this.leftLeg.rotateAngleY = -((float)Math.PI / 10F);
            this.leftLeg.rotateAngleZ = -0.07853982F;
        }

        this.rightArm.rotateAngleY = 0.0F;
        this.rightArm.rotateAngleZ = 0.0F;

        switch (this.leftArmPose)
        {
            case EMPTY:
                this.leftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - 0.9424779F;
                this.leftArm.rotateAngleY = 0.5235988F;
                break;
            case ITEM:
                this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                this.leftArm.rotateAngleY = 0.0F;
        }

        switch (this.rightArmPose)
        {
            case EMPTY:
                this.rightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - 0.9424779F;
                this.rightArm.rotateAngleY = -0.5235988F;
                break;
            case ITEM:
                this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                this.rightArm.rotateAngleY = 0.0F;
        }

        if (this.swingProgress > 0.0F)
        {
            EnumHandSide enumhandside = this.getMainHand(entityIn);
            ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
            float f1 = this.swingProgress;
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

            if (enumhandside == EnumHandSide.LEFT)
            {
                this.body.rotateAngleY *= -1.0F;
            }

            this.rightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleX += this.body.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float)Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
            modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
        }

        if (this.isSneak)
        {
            this.body.rotateAngleX = 0.5F;
            this.rightArm.rotateAngleX += 0.4F;
            this.leftArm.rotateAngleX += 0.4F;
            this.rightLeg.rotationPointZ = 4.0F;
            this.leftLeg.rotationPointZ = 4.0F;
            this.rightLeg.rotationPointY = 9.0F;
            this.leftLeg.rotationPointY = 9.0F;
            this.head.rotationPointY = 1.0F;
        }
        else
        {
            this.body.rotateAngleX = 0.0F;
            this.rightLeg.rotationPointZ = 0.1F;
            this.leftLeg.rotationPointZ = 0.1F;
            this.rightLeg.rotationPointY = 12.0F;
            this.leftLeg.rotationPointY = 12.0F;
            this.head.rotationPointY = 0.0F;
        }

        this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY;
            this.leftArm.rotateAngleY = 0.1F + this.head.rotateAngleY + 0.4F;
            this.rightArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
            this.leftArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
        }
        else if (this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY - 0.4F;
            this.leftArm.rotateAngleY = 0.1F + this.head.rotateAngleY;
            this.rightArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
            this.leftArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
        }

        // Zombie arm rotation
        boolean check = entityIn instanceof EntityZombie && ((EntityZombie)entityIn).isArmsRaised();
        float g = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.rightArm.rotateAngleY = -(0.1F - g * 0.6F);
        this.leftArm.rotateAngleY = 0.1F - g * 0.6F;
        float f2 = -(float)Math.PI / (check ? 1.5F : 2.25F);
        this.rightArm.rotateAngleX = f2;
        this.leftArm.rotateAngleX = f2;
        this.rightArm.rotateAngleX += g * 1.2F - f1 * 0.4F;
        this.leftArm.rotateAngleX += g * 1.2F - f1 * 0.4F;
        this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
    
    public void setModelAttributes(ModelBase model)
    {
        super.setModelAttributes(model);

        if (model instanceof ModelIceZombie)
        {
            ModelIceZombie modelbiped = (ModelIceZombie)model;
            this.leftArmPose = modelbiped.leftArmPose;
            this.rightArmPose = modelbiped.rightArmPose;
            this.isSneak = modelbiped.isSneak;
        }
    }
    
    public void setVisible(boolean visible)
    {
        this.head.showModel = visible;
        this.body.showModel = visible;
        this.rightArm.showModel = visible;
        this.leftArm.showModel = visible;
        this.rightLeg.showModel = visible;
        this.leftLeg.showModel = visible;
        this.shape8.showModel = visible;
        this.shape9.showModel = visible;
        this.shape10.showModel = visible;
        this.shape11.showModel = visible;
        this.shape12.showModel = visible;
        this.shape13.showModel = visible;
    }
        
    public void postRenderArm(float scale, EnumHandSide side)
    {
        this.getArmForSide(side).postRender(scale);
    }

    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.leftArm : this.rightArm;
    }

    protected EnumHandSide getMainHand(Entity entityIn)
    {
        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
            EnumHandSide enumhandside = entitylivingbase.getPrimaryHand();
            return entitylivingbase.swingingHand == EnumHand.MAIN_HAND ? enumhandside : enumhandside.opposite();
        }
        else
        {
            return EnumHandSide.RIGHT;
        }
    }
}
