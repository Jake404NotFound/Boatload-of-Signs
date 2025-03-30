package net.mcreator.boatloadofsigns.ui;

import net.mcreator.element.ModElementType;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.element.types.HangingSign;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JEmptyBox;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.renderer.ModelComboBoxRenderer;
import net.mcreator.ui.minecraft.SoundSelector;
import net.mcreator.ui.minecraft.TextureHolder;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.util.StringUtils;
import net.mcreator.workspace.elements.ModElement;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HangingSignGUI extends ModElementGUI<HangingSign> {

    private TextureHolder texture;
    private TextureHolder textureBack;
    private TextureHolder chainTexture;
    private final VTextField name = new VTextField(20);
    private final JSpinner maxStackSize = new JSpinner(new SpinnerNumberModel(16, 1, 64, 1));
    private final JSpinner lightLevel = new JSpinner(new SpinnerNumberModel(0, 0, 15, 1));
    private final JComboBox<String> material = new JComboBox<>(new String[]{"WOOD", "METAL", "STONE"});
    private final JCheckBox waterloggable = L10N.checkbox("elementgui.common.enable");
    private final JComboBox<String> requiredTool = new JComboBox<>(new String[]{"AXE", "PICKAXE", "NONE"});
    private final SoundSelector customSound = new SoundSelector(mcreator);
    private final SearchableComboBox<TabEntry> creativeTab = new SearchableComboBox<>();

    private final ValidationGroup page1group = new ValidationGroup();

    public HangingSignGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        texture = new TextureHolder(new File(""), 32, "Hanging sign texture", mcreator);
        textureBack = new TextureHolder(new File(""), 32, "Hanging sign back texture (optional)", mcreator);
        chainTexture = new TextureHolder(new File(""), 32, "Chain texture (optional)", mcreator);

        JPanel pane1 = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;

        name.setValidator(new TextFieldValidator(name, L10N.t("elementgui.common.error_name_missing")));
        name.enableRealtimeValidation();
        page1group.addValidationElement(name);

        ComponentUtils.deriveFont(name, 16);

        JPanel namePanel = new JPanel(new GridBagLayout());
        namePanel.setOpaque(false);

        namePanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/gui_name"),
                L10N.label("elementgui.common.name_in_gui")), new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
        namePanel.add(name, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(8, 0, 8, 0), 0, 0));

        panel.add(namePanel, gbc);

        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/texture"),
                L10N.label("elementgui.common.texture")), gbc);
        gbc.gridy++;
        panel.add(PanelUtils.centerInPanel(texture), gbc);

        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("block/texture_back"),
                L10N.label("elementgui.common.texture_back")), gbc);
        gbc.gridy++;
        panel.add(PanelUtils.centerInPanel(textureBack), gbc);

        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("block/chain_texture"),
                L10N.label("elementgui.common.chain_texture")), gbc);
        gbc.gridy++;
        panel.add(PanelUtils.centerInPanel(chainTexture), gbc);

        gbc.gridy++;
        panel.add(new JEmptyBox(20, 20), gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/creative_tab"),
                L10N.label("elementgui.common.creative_tab")), gbc);
        gbc.gridx++;
        panel.add(creativeTab, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/max_stack_size"),
                L10N.label("elementgui.common.max_stack_size")), gbc);
        gbc.gridx++;
        panel.add(maxStackSize, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("block/material"),
                L10N.label("elementgui.common.material")), gbc);
        gbc.gridx++;
        panel.add(material, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("block/waterloggable"),
                L10N.label("elementgui.common.waterloggable")), gbc);
        gbc.gridx++;
        panel.add(waterloggable, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("block/light_level"),
                L10N.label("elementgui.common.light_level")), gbc);
        gbc.gridx++;
        panel.add(lightLevel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("block/required_tool"),
                L10N.label("elementgui.common.required_tool")), gbc);
        gbc.gridx++;
        panel.add(requiredTool, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("common/custom_sound"),
                L10N.label("elementgui.common.custom_sound")), gbc);
        gbc.gridx++;
        panel.add(customSound, gbc);

        pane1.add(PanelUtils.totalCenterInPanel(panel));
        pane1.setOpaque(false);

        addPage(L10N.t("elementgui.common.page_properties"), pane1);

        if (!isEditingMode()) {
            String readableNameFromModElement = StringUtils.machineToReadableName(modElement.getName());
            name.setText(readableNameFromModElement);
        }
    }

    @Override
    public void reloadDataLists() {
        super.reloadDataLists();
        ComboBoxUtil.updateComboBoxContents(creativeTab, ElementUtil.loadAllTabs(mcreator.getWorkspace()),
                new TabEntry(mcreator.getWorkspace(), "CUSTOM", "Custom"));
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        if (page == 0)
            return new AggregatedValidationResult(page1group);
        return new AggregatedValidationResult.PASS();
    }

    @Override
    public HangingSign getElementFromGUI() {
        HangingSign sign = new HangingSign(modElement);
        sign.name = name.getText();
        sign.texture = texture.getID();
        sign.textureBack = textureBack.getID();
        sign.chainTexture = chainTexture.getID();
        sign.creativeTab = new TabEntry(mcreator.getWorkspace(), creativeTab.getSelectedItem());
        sign.maxStackSize = (int) maxStackSize.getValue();
        sign.material = (String) material.getSelectedItem();
        sign.waterloggable = waterloggable.isSelected();
        sign.lightLevel = (int) lightLevel.getValue();
        sign.requiredTool = (String) requiredTool.getSelectedItem();
        sign.customSound = customSound.getSound();
        return sign;
    }

    @Override
    public void setElementFromGUI(HangingSign sign) {
        name.setText(sign.name);
        texture.setTextureFromID(sign.texture);
        textureBack.setTextureFromID(sign.textureBack);
        chainTexture.setTextureFromID(sign.chainTexture);
        creativeTab.setSelectedItem(sign.creativeTab);
        maxStackSize.setValue(sign.maxStackSize);
        material.setSelectedItem(sign.material);
        waterloggable.setSelected(sign.waterloggable);
        lightLevel.setValue(sign.lightLevel);
        requiredTool.setSelectedItem(sign.requiredTool);
        customSound.setSound(sign.customSound);
    }

    @Override
    public ModElementType<?> getModElementType() {
        return ModElementType.CUSTOM;
    }
}
