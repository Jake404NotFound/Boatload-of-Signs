<#-- @ftl {"imports":<#include "procedures.java_imports.ftl">} -->
package ${package}.block;

import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ${package}.block.entity.${name}BlockEntity;
import ${package}.init.${JavaModName}BlockEntities;

public class ${name}Block extends CeilingHangingSignBlock {
    private static final WoodType WOOD_TYPE = WoodType.create("${registryname}");
    
    public ${name}Block() {
        <#if material == "WOOD">
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1f).sound(SoundType.WOOD)
        <#elseif material == "METAL">
        super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noCollission().strength(1.5f).sound(SoundType.METAL)
        <#else>
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noCollission().strength(1.5f).sound(SoundType.STONE)
        </#if>
            <#if lightLevel != 0>.lightLevel(s -> ${lightLevel})</#if>
            <#if waterloggable>.pushReaction(PushReaction.DESTROY)</#if>
            <#if requiredTool == "AXE">.requiresCorrectToolForDrops()</#if>
            <#if requiredTool == "PICKAXE">.requiresCorrectToolForDrops()</#if>
            , WOOD_TYPE);
            
        <#if waterloggable>
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0).setValue(WATERLOGGED, false).setValue(ATTACHED, false));
        </#if>
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ${name}BlockEntity(pos, state);
    }
    
    <#if waterloggable>
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, WATERLOGGED, ATTACHED);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
            .setValue(ROTATION, Integer.valueOf((int) (context.getRotation() * 16.0F / 360.0F + 0.5D) & 15))
            .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER)
            .setValue(ATTACHED, false);
    }
    
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }
    
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
    </#if>
    
    public static class Wall extends WallHangingSignBlock {
        public Wall() {
            <#if material == "WOOD">
            super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1f).sound(SoundType.WOOD)
            <#elseif material == "METAL">
            super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noCollission().strength(1.5f).sound(SoundType.METAL)
            <#else>
            super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noCollission().strength(1.5f).sound(SoundType.STONE)
            </#if>
                <#if lightLevel != 0>.lightLevel(s -> ${lightLevel})</#if>
                <#if waterloggable>.pushReaction(PushReaction.DESTROY)</#if>
                <#if requiredTool == "AXE">.requiresCorrectToolForDrops()</#if>
                <#if requiredTool == "PICKAXE">.requiresCorrectToolForDrops()</#if>
                , WOOD_TYPE);
                
            <#if waterloggable>
            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
            </#if>
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ${name}BlockEntity(pos, state);
        }
        
        <#if waterloggable>
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING, WATERLOGGED);
        }
        
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            BlockState blockstate = this.defaultBlockState();
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            LevelReader world = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            Direction[] adirection = context.getNearestLookingDirections();

            for (Direction direction : adirection) {
                if (direction.getAxis().isHorizontal()) {
                    Direction direction1 = direction.getOpposite();
                    blockstate = blockstate.setValue(FACING, direction1);
                    if (blockstate.canSurvive(world, blockpos)) {
                        return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                    }
                }
            }

            return null;
        }
        
        @Override
        public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
            if (state.getValue(WATERLOGGED)) {
                world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
            }
            return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
        }
        
        @Override
        public FluidState getFluidState(BlockState state) {
            return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
        }
        </#if>
    }
}
