package net.mcreator.ui.modgui;

import net.mcreator.element.types.HangingSign;
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

public class HangingSignGUI extends ModElementGUI<HangingSign> {

    private TextureHolder texture;
    private TextureHolder textureBack;
    private TextureHolder chainTexture;
    private final VTextField name = new VTextField(20);
    private final JSpinner lightLevel = new JSpinner(new SpinnerNumberModel(0, 0, 15, 1));
    private final JCheckBox waterloggable = L10N.checkbox("elementgui.common.enable");
    private MaterialTextureSelector material;
    private JComboBox<String> requiredTool;
    private SoundSelector customSound;

    public HangingSignGUI(ModElement modElement) {
        super(modElement);
        this.initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        texture = new TextureHolder(new GeneralImageThumbnailer(), getModElement().getWorkspace());
        textureBack = new TextureHolder(new GeneralImageThumbnailer(), getModElement().getWorkspace());
        chainTexture = new TextureHolder(new GeneralImageThumbnailer(), getModElement().getWorkspace());
        material = new MaterialTextureSelector(getModElement().getWorkspace());
        requiredTool = new JComboBox<>(new String[]{"AXE", "PICKAXE", "SHOVEL", "HOE", "NONE"});
        customSound = new SoundSelector(getModElement().getWorkspace());

        JPanel pane = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("hanging_sign/name"),
                L10N.label("elementgui.common.name")));
        mainPanel.add(name);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("hanging_sign/texture"),
                L10N.label("elementgui.hangingsign.texture")));
        mainPanel.add(texture);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("hanging_sign/texture_back"),
                L10N.label("elementgui.hangingsign.texture_back")));
        mainPanel.add(textureBack);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("hanging_sign/chain_texture"),
                L10N.label("elementgui.hangingsign.chain_texture")));
        mainPanel.add(chainTexture);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("hanging_sign/material"),
                L10N.label("elementgui.hangingsign.material")));
        mainPanel.add(material);

        JPanel secondPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("hanging_sign/waterloggable"),
                L10N.label("elementgui.hangingsign.waterloggable")));
        secondPanel.add(waterloggable);

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("hanging_sign/light_level"),
                L10N.label("elementgui.hangingsign.light_level")));
        secondPanel.add(lightLevel);

        secondPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("hanging_sign/required_tool"),
                L10N.label("elementgui.hangingsign.required_tool")));
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
    public HangingSign getElementFromGUI() {
        HangingSign hangingSign = new HangingSign(getModElement());
        hangingSign.name = name.getText();
        hangingSign.texture = texture.getID();
        hangingSign.textureBack = textureBack.getID();
        hangingSign.chainTexture = chainTexture.getID();
        hangingSign.waterloggable = waterloggable.isSelected();
        hangingSign.lightLevel = (int) lightLevel.getValue();
        hangingSign.material = material.getBlock();
        hangingSign.requiredTool = (String) requiredTool.getSelectedItem();
        hangingSign.customSound = customSound.getSound();
        return hangingSign;
    }

    @Override
    public void setElementFromGUI(HangingSign hangingSign) {
        name.setText(hangingSign.name);
        texture.setTextureFromTextureName(hangingSign.texture);
        textureBack.setTextureFromTextureName(hangingSign.textureBack);
        chainTexture.setTextureFromTextureName(hangingSign.chainTexture);
        waterloggable.setSelected(hangingSign.waterloggable);
        lightLevel.setValue(hangingSign.lightLevel);
        material.setBlock(hangingSign.material);
        requiredTool.setSelectedItem(hangingSign.requiredTool);
        customSound.setSound(hangingSign.customSound);
    }
}
