package net.mcreator.boatloadofsigns;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.ModElementType;
import net.mcreator.element.types.CustomSign;
import net.mcreator.element.types.HangingSign;
import net.mcreator.element.types.CustomBoat;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.ui.modgui.CustomSignGUI;
import net.mcreator.ui.modgui.HangingSignGUI;
import net.mcreator.ui.modgui.CustomBoatGUI;
import net.mcreator.workspace.elements.ModElement;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.List;

public class Launcher extends JavaPlugin {

    public Launcher() {
        super();
        
        // Register mod element types
        ModElementType.REGISTRY.register(
            new ModElementType<>("customsign", CustomSign.class, CustomSignGUI.class)
                .setIcon("sign")
                .setDescription("Custom Sign")
        );
        
        ModElementType.REGISTRY.register(
            new ModElementType<>("hangingsign", HangingSign.class, HangingSignGUI.class)
                .setIcon("hanging_sign")
                .setDescription("Hanging Sign")
        );
        
        ModElementType.REGISTRY.register(
            new ModElementType<>("customboat", CustomBoat.class, CustomBoatGUI.class)
                .setIcon("boat")
                .setDescription("Custom Boat")
        );
    }

    @Override
    public @Nullable JComponent getModElementGUI(ModElement modElement) {
        if (modElement.getType().equals("customsign")) {
            return new CustomSignGUI(modElement);
        } else if (modElement.getType().equals("hangingsign")) {
            return new HangingSignGUI(modElement);
        } else if (modElement.getType().equals("customboat")) {
            return new CustomBoatGUI(modElement);
        }
        return null;
    }
}
