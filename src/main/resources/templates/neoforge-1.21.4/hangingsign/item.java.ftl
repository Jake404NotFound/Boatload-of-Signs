<#-- @ftl {"imports":<#include "procedures.java_imports.ftl">} -->
package ${package}.item;

import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import ${package}.block.${name}Block;
import ${package}.init.${JavaModName}Blocks;

public class ${name}Item extends HangingSignItem {
    public ${name}Item() {
        super(new Item.Properties().stacksTo(${maxStackSize}), 
              ${JavaModName}Blocks.${registryname?upper_case}, 
              ${JavaModName}Blocks.${registryname?upper_case}_WALL);
    }
}
