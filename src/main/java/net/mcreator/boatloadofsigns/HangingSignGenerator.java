package net.mcreator.boatloadofsigns;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.types.HangingSign;
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

public class HangingSignGenerator implements GeneratorTemplate {

    @Override
    public List<GeneratorFile> generateElement(GeneratableElement element, 
                                              TemplateGenerator templateGenerator, 
                                              TemplateGeneratorConfiguration configuration) 
                                              throws TemplateGeneratorException {
        
        HangingSign sign = (HangingSign) element;
        List<GeneratorFile> generatorFiles = new ArrayList<>();
        
        // Create data model for templates
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("sign", sign);
        dataModel.put("name", sign.name);
        dataModel.put("modid", element.getModElement().getWorkspace().getWorkspaceSettings().getModID());
        dataModel.put("registryname", element.getModElement().getRegistryName());
        dataModel.put("texture", sign.texture);
        dataModel.put("textureBack", sign.textureBack);
        dataModel.put("chainTexture", sign.chainTexture);
        dataModel.put("maxStackSize", sign.maxStackSize);
        dataModel.put("material", sign.material);
        dataModel.put("waterloggable", sign.waterloggable);
        dataModel.put("lightLevel", sign.lightLevel);
        dataModel.put("requiredTool", sign.requiredTool);
        dataModel.put("customSound", sign.customSound);
        
        // Generate block class
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/hangingsign/block.java.ftl",
            element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
            + "/block/" + element.getModElement().getName() + "Block.java",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate item class
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/hangingsign/item.java.ftl",
            element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
            + "/item/" + element.getModElement().getName() + "Item.java",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate block entity class
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/hangingsign/blockentity.java.ftl",
            element.getModElement().getSourceRoot() + "/" + element.getModElement().getPackageName()
            + "/block/entity/" + element.getModElement().getName() + "BlockEntity.java",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate block model
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/hangingsign/blockmodel.json.ftl",
            element.getModElement().getResourceRoot() + "/assets/" + dataModel.get("modid")
            + "/models/block/" + element.getModElement().getRegistryName() + ".json",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate item model
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/hangingsign/itemmodel.json.ftl",
            element.getModElement().getResourceRoot() + "/assets/" + dataModel.get("modid")
            + "/models/item/" + element.getModElement().getRegistryName() + ".json",
            dataModel, configuration.getGeneratorName()
        ));
        
        // Generate blockstate
        generatorFiles.add(new GeneratorFile(
            "templates/neoforge-1.21.4/hangingsign/blockstate.json.ftl",
            element.getModElement().getResourceRoot() + "/assets/" + dataModel.get("modid")
            + "/blockstates/" + element.getModElement().getRegistryName() + ".json",
            dataModel, configuration.getGeneratorName()
        ));
        
        return generatorFiles;
    }

    @Override
    public GeneratableElement getModElementFromClass(Workspace workspace, String className) {
        return null; // Not needed for this implementation
    }
}
