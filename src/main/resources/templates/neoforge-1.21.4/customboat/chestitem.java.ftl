<#-- @ftl {"imports":<#include "procedures.java_imports.ftl">} -->
package ${package}.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.core.BlockPos;
import ${package}.entity.${name}ChestEntity;
import ${package}.init.${JavaModName}Items;

public class ${name}ChestItem extends Item {
    
    public ${name}ChestItem() {
        super(new Item.Properties().stacksTo(${maxStackSize}));
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hitresult = player.pick(5.0D, 0.0F, false);
        
        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vec3 = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), 
                ENTITY_PREDICATE);
            
            if (!list.isEmpty()) {
                Vec3 vec31 = player.getEyePosition();
                
                for(Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }
            
            if (hitresult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockhitresult = (BlockHitResult)hitresult;
                BlockPos blockpos = blockhitresult.getBlockPos();
                
                if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
                    ${name}ChestEntity boat = new ${name}ChestEntity(level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
                    boat.setYRot(player.getYRot());
                    
                    if (!level.noCollision(boat, boat.getBoundingBox())) {
                        return InteractionResultHolder.fail(itemstack);
                    } else {
                        if (!level.isClientSide) {
                            level.addFreshEntity(boat);
                            level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
                            
                            if (!player.getAbilities().instabuild) {
                                itemstack.shrink(1);
                            }
                        }
                        
                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                    }
                }
            }
            
            return InteractionResultHolder.pass(itemstack);
        }
    }
}
