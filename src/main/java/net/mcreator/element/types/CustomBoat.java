package net.mcreator.element.types;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.workspace.elements.ModElement;

import java.util.Objects;

public class CustomBoat extends GeneratableElement {

    public String name;
    public String texture;
    public TabEntry creativeTab;
    public int maxStackSize;
    public boolean hasChest;
    public double speedMultiplier;
    public int durability;
    public String material;
    public double buoyancy;

    public CustomBoat(ModElement element) {
        super(element);
        
        this.name = "";
        this.texture = "";
        this.creativeTab = null;
        this.maxStackSize = 1;
        this.hasChest = false;
        this.speedMultiplier = 1.0;
        this.durability = 40;
        this.material = "WOOD";
        this.buoyancy = 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomBoat that = (CustomBoat) o;
        return maxStackSize == that.maxStackSize && 
               hasChest == that.hasChest && 
               Double.compare(that.speedMultiplier, speedMultiplier) == 0 && 
               durability == that.durability && 
               Double.compare(that.buoyancy, buoyancy) == 0 && 
               Objects.equals(name, that.name) && 
               Objects.equals(texture, that.texture) && 
               Objects.equals(creativeTab, that.creativeTab) && 
               Objects.equals(material, that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, texture, creativeTab, maxStackSize, 
                           hasChest, speedMultiplier, durability, material, buoyancy);
    }
}
