package net.mcreator.ui.modgui;

import net.mcreator.element.types.CustomSign;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.TextureHolder;
import net.mcreator.ui.minecraft.MaterialTextureSelector;
import net.mcreator.ui.minecraft.SoundSelector;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.workspace.elements.ModElement;

import javax.swing.*;
import java.awt.*;

public class CustomSignGUI extends ModElementGUI<CustomSign> {

    private TextureHolder texture;
    private TextureHolder textureBack;
    private final VTextField name = new VTextField(20);
    private final JSpinner lightLevel = new JSpinner(new SpinnerNumberModel(0, 0, 15, 1));
    private final JCheckBox waterloggable = L10N.checkbox("elementgui.common.enable");
    private MaterialTextureSelector material;
    private JComboBox<String> requiredTool;
    private SoundSelector customSound;

    public CustomSignGUI(ModElement modElement) {
        super(modElement);
        this.initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        texture = new TextureHolder(new GeneralImageThumbnailer(), getModElement().getWorkspace());
        textureBack = new TextureHolder(new GeneralImageThumbnailer(), getModElement().getWorkspace());
        material = new MaterialTextureSelector(getModElement().getWorkspace());
        requiredTool = new JComboBox<>(new String[]{"AXE", "PICKAXE", "SHOVEL", "HOE", "NONE"});
        customSound = new SoundSelector(getModElement().getWorkspace());

        JPanel pane = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_sign/name"),
                L10N.label("elementgui.common.name")));
        mainPanel.add(name);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_sign/texture"),
                L10N.label("elementgui.customsign.texture")));
        mainPanel.add(texture);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_sign/texture_back"),
                L10N.label("elementgui.customsign.texture_back")));
        mainPanel.add(textureBack);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_sign/material"),
                L10N.label("elementgui.customsign.material")));
        mainPanel.add(material);

        JPanel secondPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_sign/waterloggable"),
                L10N.label("elementgui.customsign.waterloggable")));
        secondPanel.add(waterloggable);

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_sign/light_level"),
                L10N.label("elementgui.customsign.light_level")));
        secondPanel.add(lightLevel);

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("custom_sign/required_tool"),
                L10N.label("elementgui.customsign.required_tool")));
        secondPanel.add(requiredTool);

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
        customSound.refreshListKeepSelected();
    }

    @Override
    public CustomSign getElementFromGUI() {
        CustomSign customSign = new CustomSign(getModElement());
        customSign.name = name.getText();
        customSign.texture = texture.getID();
        customSign.textureBack = textureBack.getID();
        customSign.waterloggable = waterloggable.isSelected();
        customSign.lightLevel = (int) lightLevel.getValue();
        customSign.material = material.getBlock();
        customSign.requiredTool = (String) requiredTool.getSelectedItem();
        customSign.customSound = customSound.getSound();
        return customSign;
    }

    @Override
    public void setElementFromGUI(CustomSign customSign) {
        name.setText(customSign.name);
        texture.setTextureFromTextureName(customSign.texture);
        textureBack.setTextureFromTextureName(customSign.textureBack);
        waterloggable.setSelected(customSign.waterloggable);
        lightLevel.setValue(customSign.lightLevel);
        material.setBlock(customSign.material);
        requiredTool.setSelectedItem(customSign.requiredTool);
        customSound.setSound(customSign.customSound);
    }
}
