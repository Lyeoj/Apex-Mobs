package lyeoj.apexmobs.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySniperArrow extends EntityTippedArrow {
	
	private int flightTime = 10;
	private static final DataParameter<Integer> LINETIME = EntityDataManager.<Integer>createKey(EntitySniperArrow.class, DataSerializers.VARINT);
	private static final DataParameter<Float> INIT_X = EntityDataManager.<Float>createKey(EntitySniperArrow.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> INIT_Y = EntityDataManager.<Float>createKey(EntitySniperArrow.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> INIT_Z = EntityDataManager.<Float>createKey(EntitySniperArrow.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> HEADING_X = EntityDataManager.<Float>createKey(EntitySniperArrow.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> HEADING_Y = EntityDataManager.<Float>createKey(EntitySniperArrow.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> HEADING_Z = EntityDataManager.<Float>createKey(EntitySniperArrow.class, DataSerializers.FLOAT);

	public EntitySniperArrow(World worldIn) {
		super(worldIn);
		this.setNoGravity(true);
	}
	
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(INIT_X, 0.0F);
		this.dataManager.register(INIT_Y, 0.0F);
		this.dataManager.register(INIT_Z, 0.0F);
		this.dataManager.register(HEADING_X, 0.0F);
		this.dataManager.register(HEADING_Y, 0.0F);
		this.dataManager.register(HEADING_Z, 0.0F);
		
		this.dataManager.register(LINETIME, 0);
	}
	
	public void setInit(float x, float y, float z) {
		this.getDataManager().set(INIT_X, x);
		this.getDataManager().set(INIT_Y, y);
		this.getDataManager().set(INIT_Z, z);
	}
	
	public Vec3d getInit() {
		Vec3d init = new Vec3d((double)this.getDataManager().get(INIT_X), (double)this.getDataManager().get(INIT_Y), (double)this.getDataManager().get(INIT_Z));
		return init;
	}
	
	public void setHeading(float x, float y, float z) {
		this.getDataManager().set(HEADING_X, x);
		this.getDataManager().set(HEADING_Y, y);
		this.getDataManager().set(HEADING_Z, z);
	}
	
	public Vec3d getHeading() {
		Vec3d init = new Vec3d((double)this.getDataManager().get(HEADING_X), (double)this.getDataManager().get(HEADING_Y), (double)this.getDataManager().get(HEADING_Z));
		return init;
	}
	
	public int getLineTime() {
		return this.getDataManager().get(LINETIME);
	}
	
	public void incLineTime() {
		this.getDataManager().set(LINETIME, this.getDataManager().get(LINETIME) + 1);
	}
	
	public EntitySniperArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
    }

    public EntitySniperArrow(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
        this.setNoGravity(true);
    }
    
    public void onUpdate() {
    	if(flightTime > 0) {
    		flightTime--;
    	} else {
    		this.setNoGravity(false);
    	}
    	super.onUpdate();
    	if(this.getLineTime() < 30) {
    		if(this.world.isRemote) {
        		for(int i = 1; i < 51; i++) {
        			this.world.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, this.getInit().x + (this.getHeading().x * 0.2 * i), this.getInit().y + (this.getHeading().y * 0.2 * i), this.getInit().z + (this.getHeading().z * 0.2 * i), 0, 0, 0);
        		}
        	}
    		this.incLineTime();
    	}
    }
    
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
    	super.setThrowableHeading(x, y, z, velocity, inaccuracy);
    	this.setHeading((float)x, (float)y, (float)z); 
    	this.setInit((float)this.posX, (float)this.posY, (float)this.posZ);
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        compound.setFloat("init_x", this.dataManager.get(INIT_X));
        compound.setFloat("init_y", this.dataManager.get(INIT_Y));
        compound.setFloat("init_z", this.dataManager.get(INIT_Z));
        
        compound.setFloat("heading_x", this.dataManager.get(HEADING_X));
        compound.setFloat("heading_y", this.dataManager.get(HEADING_Y));
        compound.setFloat("heading_z", this.dataManager.get(HEADING_Z));
        
        compound.setInteger("linetime", this.dataManager.get(LINETIME));
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);        
        if (compound.hasKey("init_x", 99))
        {
            this.dataManager.set(INIT_X, compound.getFloat("init_x"));
        }
        if (compound.hasKey("init_y", 99))
        {
            this.dataManager.set(INIT_Y, compound.getFloat("init_y"));
        }
        if (compound.hasKey("init_z", 99))
        {
            this.dataManager.set(INIT_Z, compound.getFloat("init_z"));
        }
        
        if (compound.hasKey("heading_x", 99))
        {
            this.dataManager.set(HEADING_X, compound.getFloat("heading_x"));
        }
        if (compound.hasKey("heading_y", 99))
        {
            this.dataManager.set(HEADING_Y, compound.getFloat("heading_y"));
        }
        if (compound.hasKey("heading_z", 99))
        {
            this.dataManager.set(HEADING_Z, compound.getFloat("heading_z"));
        }
        
        if (compound.hasKey("linetime", 99))
        {
            this.dataManager.set(LINETIME, compound.getInteger("linetime"));
        }

    }
    
    


}
