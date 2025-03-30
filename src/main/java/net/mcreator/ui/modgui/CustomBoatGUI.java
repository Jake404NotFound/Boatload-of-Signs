package net.mcreator.ui.modgui;

import net.mcreator.element.types.CustomBoat;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.TextureHolder;
import net.mcreator.ui.minecraft.MaterialTextureSelector;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.workspace.elements.ModElement;

import javax.swing.*;
import java.awt.*;

public class CustomBoatGUI extends ModElementGUI<CustomBoat> {

    private TextureHolder texture;
    private final VTextField name = new VTextField(20);
    private final JSpinner speedMultiplier = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 5.0, 0.1));
    private final JSpinner durability = new JSpinner(new SpinnerNumberModel(40, 1, 1000, 1));
    private final JSpinner buoyancy = new JSpinner(new SpinnerNumberModel(0.0, -1.0, 1.0, 0.05));
    private final JCheckBox hasChest = L10N.checkbox("elementgui.common.enable");
    private MaterialTextureSelector material;

    public CustomBoatGUI(ModElement modElement) {
        super(modElement);
        this.initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        texture = new TextureHolder(new GeneralImageThumbnailer(), getModElement().getWorkspace());
        material = new MaterialTextureSelector(getModElement().getWorkspace());

        JPanel pane = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_boat/name"),
                L10N.label("elementgui.common.name")));
        mainPanel.add(name);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_boat/texture"),
                L10N.label("elementgui.customboat.texture")));
        mainPanel.add(texture);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_boat/material"),
                L10N.label("elementgui.customboat.material")));
        mainPanel.add(material);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_boat/has_chest"),
                L10N.label("elementgui.customboat.has_chest")));
        mainPanel.add(hasChest);

        JPanel secondPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_boat/speed_multiplier"),
                L10N.label("elementgui.customboat.speed_multiplier")));
        secondPanel.add(speedMultiplier);

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_boat/durability"),
                L10N.label("elementgui.customboat.durability")));
        secondPanel.add(durability);

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_boat/buoyancy"),
                L10N.label("elementgui.customboat.buoyancy")));
        secondPanel.add(buoyancy);

        pane.add(PanelUtils.totalCenterInPanel(PanelUtils.northAndCenterElement(
                PanelUtils.join(mainPanel, secondPanel), new JEmptyBox())));
        
        addPage(pane);

        name.setValidator(new TextFieldValidator(name, L10N.t("elementgui.common.error_name_missing")));
        name.enableRealtimeValidation();
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult(name);
    }

    @Override
    public void reloadDataLists() {
        super.reloadDataLists();
    }

    @Override
    public CustomBoat getElementFromGUI() {
        CustomBoat customBoat = new CustomBoat(getModElement());
        customBoat.name = name.getText();
        customBoat.texture = texture.getID();
        customBoat.hasChest = hasChest.isSelected();
        customBoat.speedMultiplier = (double) speedMultiplier.getValue();
        customBoat.durability = (int) durability.getValue();
        customBoat.material = material.getBlock();
        customBoat.buoyancy = (double) buoyancy.getValue();
        return customBoat;
    }

    @Override
    public void setElementFromGUI(CustomBoat customBoat) {
        name.setText(customBoat.name);
        texture.setTextureFromTextureName(customBoat.texture);
        hasChest.setSelected(customBoat.hasChest);
        speedMultiplier.setValue(customBoat.speedMultiplier);
        durability.setValue(customBoat.durability);
        material.setBlock(customBoat.material);
        buoyancy.setValue(customBoat.buoyancy);
    }
}
