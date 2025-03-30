package net.mcreator.boatloadofsigns;

import net.mcreator.boatloadofsigns.ui.CustomSignGUI;
import net.mcreator.boatloadofsigns.ui.HangingSignGUI;
import net.mcreator.boatloadofsigns.ui.CustomBoatGUI;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.types.CustomSign;
import net.mcreator.element.types.HangingSign;
import net.mcreator.element.types.CustomBoat;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.mcreator.plugin.events.ui.ModElementGUIEvent;
import net.mcreator.plugin.events.workspace.WorkspaceLoadedEvent;
import net.mcreator.plugin.modapis.ModAPIManager;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.workspace.elements.ModElement;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.List;

public class BoatloadOfSignsPlugin extends JavaPlugin {

    public BoatloadOfSignsPlugin() {
        super();
        addListener(WorkspaceLoadedEvent.class, event -> {
            // Register mod element types when workspace is loaded
            event.getWorkspace().getModElementTypes().add("customsign");
            event.getWorkspace().getModElementTypes().add("hangingsign");
            event.getWorkspace().getModElementTypes().add("customboat");
        });

        addListener(PreGeneratorsLoadingEvent.class, event -> {
            // Register generators
            ModAPIManager modAPIManager = event.getMCreator().getModAPIManager();
            
            // Register custom sign generator
            modAPIManager.addModAPI(new CustomSignGenerator());
            
            // Register hanging sign generator
            modAPIManager.addModAPI(new HangingSignGenerator());
            
            // Register custom boat generator
            modAPIManager.addModAPI(new CustomBoatGenerator());
        });
    }

    @Override
    public @Nonnull JComponent getModElementGUI(ModElement modElement) {
        // Return the appropriate GUI component based on the mod element type
        switch (modElement.getType()) {
            case "customsign":
                return new CustomSignGUI(modElement.getWorkspace().getMCreator(), modElement, false);
            case "hangingsign":
                return new HangingSignGUI(modElement.getWorkspace().getMCreator(), modElement, false);
            case "customboat":
                return new CustomBoatGUI(modElement.getWorkspace().getMCreator(), modElement, false);
            default:
                // This should never happen as we only register for our own types
                return new JPanel();
        }
    }
}
