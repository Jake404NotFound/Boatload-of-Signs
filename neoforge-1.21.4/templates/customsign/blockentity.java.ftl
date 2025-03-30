<#-- @ftl {"imports":<#include "procedures.java_imports.ftl">} -->
package ${package}.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ${package}.init.${JavaModName}BlockEntities;

public class ${name}BlockEntity extends SignBlockEntity {
    public ${name}BlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ${JavaModName}BlockEntities.${registryname?upper_case}_SIGN.get();
    }
}
