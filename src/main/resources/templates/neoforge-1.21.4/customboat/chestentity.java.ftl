<#-- @ftl {"imports":<#include "procedures.java_imports.ftl">} -->
package ${package}.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.damagesource.DamageSource;
import ${package}.init.${JavaModName}Items;
import ${package}.init.${JavaModName}EntityTypes;

public class ${name}ChestEntity extends ChestBoat {
    
    public ${name}ChestEntity(EntityType<? extends ChestBoat> type, Level level) {
        super(type, level);
    }

    public ${name}ChestEntity(Level level, double x, double y, double z) {
        this(${JavaModName}EntityTypes.${registryname?upper_case}_CHEST.get(), level);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Item getDropItem() {
        return ${JavaModName}Items.${registryname?upper_case}_CHEST.get();
    }
    
    @Override
    protected void checkFallDamage(double y, boolean onGround, org.bukkit.block.Block state, BlockPos pos) {
        // Custom fall damage logic based on durability
        if (onGround) {
            if (this.fallDistance > 3.0F) {
                this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
                
                // Apply more damage based on custom durability
                if (this.fallDistance > ${durability / 10}) {
                    this.kill();
                }
            }
            this.fallDistance = 0.0F;
        } else if (!this.level().getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < 0.0D) {
            this.fallDistance = (float) ((double) this.fallDistance - y);
        }
    }
    
    @Override
    public void controlBoat() {
        // Custom speed multiplier
        if (this.isVehicle()) {
            float f = 0.0F;
            if (this.inputLeft) {
                this.deltaRotation -= 1.0F;
            }
            if (this.inputRight) {
                this.deltaRotation += 1.0F;
            }
            if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                f += 0.005F;
            }
            this.setYRot(this.getYRot() + this.deltaRotation);
            if (this.inputUp) {
                f += 0.04F * ${speedMultiplier}; // Apply speed multiplier
            }
            if (this.inputDown) {
                f -= 0.005F;
            }
            this.setDeltaMovement(this.getDeltaMovement().add(
                (double) (Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * f),
                0.0D,
                (double) (Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * f)
            ));
            this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
        }
    }
    
    @Override
    public float getWaterLevelAbove() {
        // Custom buoyancy
        return ${buoyancy}F;
    }
    
    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Custom durability
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.level().isClientSide && !this.isRemoved()) {
            // Adjust damage based on material
            <#if material == "WOOD">
            // Wood boats take normal damage
            <#elseif material == "METAL">
            // Metal boats take less damage
            amount = amount * 0.7F;
            <#else>
            // Stone boats take more damage
            amount = amount * 1.3F;
            </#if>
            
            // Check if damage exceeds durability
            if (amount >= ${durability}) {
                this.ejectPassengers();
                this.discard();
                return true;
            }
            
            return super.hurt(source, amount);
        } else {
            return true;
        }
    }
}
