package net.mcreator.element.types;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.workspace.elements.ModElement;

import java.util.Objects;

public class CustomSign extends GeneratableElement {

    public String name;
    public String texture;
    public String textureBack;
    public TabEntry creativeTab;
    public int maxStackSize;
    public String material;
    public boolean hasGravity;
    public boolean waterloggable;
    public int lightLevel;
    public String requiredTool;
    public String customSound;

    public CustomSign(ModElement element) {
        super(element);
        
        this.name = "";
        this.texture = "";
        this.textureBack = "";
        this.creativeTab = null;
        this.maxStackSize = 16;
        this.material = "WOOD";
        this.hasGravity = false;
        this.waterloggable = true;
        this.lightLevel = 0;
        this.requiredTool = "AXE";
        this.customSound = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomSign that = (CustomSign) o;
        return maxStackSize == that.maxStackSize && 
               hasGravity == that.hasGravity && 
               waterloggable == that.waterloggable && 
               lightLevel == that.lightLevel && 
               Objects.equals(name, that.name) && 
               Objects.equals(texture, that.texture) && 
               Objects.equals(textureBack, that.textureBack) && 
               Objects.equals(creativeTab, that.creativeTab) && 
               Objects.equals(material, that.material) && 
               Objects.equals(requiredTool, that.requiredTool) && 
               Objects.equals(customSound, that.customSound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, texture, textureBack, creativeTab, maxStackSize, material, 
                           hasGravity, waterloggable, lightLevel, requiredTool, customSound);
    }
}
