package net.mcreator.boatloadofsigns;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.types.CustomBoat;
import net.mcreator.generator.GeneratorTemplate;
import net.mcreator.generator.template.TemplateGeneratorException;
import net.mcreator.generator.template.TemplateGenerator;
import net.mcreator.generator.template.TemplateGeneratorConfiguration;
import net.mcreator.generator.GeneratorFile;
import net.mcreator.generator.GeneratorConfiguration;
import net.mcreator.workspace.Workspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomBoatGenerator implements GeneratorTemplate {

    @Override
    public List<GeneratorFile> generateElement(GeneratableElement element, 
                                              TemplateGenerator templateGenerator, 
                                              TemplateGeneratorConfiguration configuration) 
                                              throws TemplateGeneratorException {
        
        CustomBoat boat = (CustomBoat) element;
        List<GeneratorFile> generatorFiles = new ArrayList<>();
        
        // Create data model for templates
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("boat", boat);
        dataModel.put("name", boat.name);
        dataModel.put("modid", element.getModElement().getWorkspace().getWorkspaceSettings().getModID());
        dataModel.put("registryname", element.getModElement().getRegistryName());
        dataModel.put("texture", boat.texture);
        dataModel.put("maxStackSize", boat.maxStackSize);
        dataModel.put("hasChest", boat.hasChest);
        dataModel.put("speedMultiplier", boat.speedMultiplier);
        dataModel.put("durability", boat.durability);
        dataModel.put("material", boat.material);
        dataModel.put("customSound", boat.customSound);
        dataModel.put("buoyancy", boat.buoyancy);
        
        // Generate entity class
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/customboat/entity.java.ftl",
            element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
            + "/entity/" + element.getModElement().getName() + "Entity.java",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate chest entity class if needed
        if (boat.hasChest) {
            generatorFiles.add(new GeneratorFile(
                "templates/neoforge-1.21.4/customboat/chestentity.java.ftl",
                element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
                + "/entity/" + element.getModElement().getName() + "ChestEntity.java",
                dataModel, configuration.getGeneratorName()
            ));
        }
        
        // Generate item class
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/customboat/item.java.ftl",
            element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
            + "/item/" + element.getModElement().getName() + "Item.java",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate chest item class if needed
        if (boat.hasChest) {
            generatorFiles.add(new GeneratorFile(
                "templates/neoforge-1.21.4/customboat/chestitem.java.ftl",
                element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
                + "/item/" + element.getModElement().getName() + "ChestItem.java",
                dataModel, configuration.getGeneratorName()
            ));
        }
        
        // Generate entity model
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/customboat/entitymodel.java.ftl",
            element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
            + "/client/model/" + element.getModElement().getName() + "Model.java",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate entity renderer
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/customboat/entityrenderer.java.ftl",
            element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
            + "/client/renderer/" + element.getModElement().getName() + "Renderer.java",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate item model
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/customboat/itemmodel.json.ftl",
            element.getModElement().getResourceRoot() + "/assets/" + dataModel.get("modid")
            + "/models/item/" + element.getModElement().getRegistryName() + ".json",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate chest item model if needed
        if (boat.hasChest) {
            generatorFiles.add(new GeneratorFile(
                "templates/neoforge-1.21.4/customboat/chestitemmodel.json.ftl",
                element.getModElement().getResourceRoot() + "/assets/" + dataModel.get("modid")
                + "/models/item/" + element.getModElement().getRegistryName() + "_chest.json",
                dataModel, configuration.getGeneratorName()
            ));
        }
        
        return generatorFiles;
    }

    @Override
    public GeneratableElement getModElementFromClass(Workspace workspace, String className) {
        return null; // Not needed for this implementation
    }
}
