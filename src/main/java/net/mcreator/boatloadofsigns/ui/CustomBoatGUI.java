package net.mcreator.boatloadofsigns.ui;

import net.mcreator.element.ModElementType;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.element.types.CustomBoat;
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

public class CustomBoatGUI extends ModElementGUI<CustomBoat> {

    private TextureHolder texture;
    private final VTextField name = new VTextField(20);
    private final JSpinner maxStackSize = new JSpinner(new SpinnerNumberModel(1, 1, 64, 1));
    private final JCheckBox hasChest = L10N.checkbox("elementgui.common.enable");
    private final JSpinner speedMultiplier = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 5.0, 0.1));
    private final JSpinner durability = new JSpinner(new SpinnerNumberModel(40, 1, 1000, 1));
    private final JComboBox<String> material = new JComboBox<>(new String[]{"WOOD", "METAL", "STONE"});
    private final SoundSelector customSound = new SoundSelector(mcreator);
    private final JSpinner buoyancy = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 5.0, 0.1));
    private final SearchableComboBox<TabEntry> creativeTab = new SearchableComboBox<>();

    private final ValidationGroup page1group = new ValidationGroup();

    public CustomBoatGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        texture = new TextureHolder(new File(""), 32, "Boat texture", mcreator);

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
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("entity/has_chest"),
                L10N.label("elementgui.customboat.has_chest")), gbc);
        gbc.gridx++;
        panel.add(hasChest, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("entity/speed_multiplier"),
                L10N.label("elementgui.customboat.speed_multiplier")), gbc);
        gbc.gridx++;
        panel.add(speedMultiplier, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("entity/durability"),
                L10N.label("elementgui.customboat.durability")), gbc);
        gbc.gridx++;
        panel.add(durability, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("block/material"),
                L10N.label("elementgui.common.material")), gbc);
        gbc.gridx++;
        panel.add(material, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(HelpUtils.wrapWithHelpButton(this.withEntry("entity/buoyancy"),
                L10N.label("elementgui.customboat.buoyancy")), gbc);
        gbc.gridx++;
        panel.add(buoyancy, gbc);

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
    public CustomBoat getElementFromGUI() {
        CustomBoat boat = new CustomBoat(modElement);
        boat.name = name.getText();
        boat.texture = texture.getID();
        boat.creativeTab = new TabEntry(mcreator.getWorkspace(), creativeTab.getSelectedItem());
        boat.maxStackSize = (int) maxStackSize.getValue();
        boat.hasChest = hasChest.isSelected();
        boat.speedMultiplier = (double) speedMultiplier.getValue();
        boat.durability = (int) durability.getValue();
        boat.material = (String) material.getSelectedItem();
        boat.customSound = customSound.getSound();
        boat.buoyancy = (double) buoyancy.getValue();
        return boat;
    }

    @Override
    public void setElementFromGUI(CustomBoat boat) {
        name.setText(boat.name);
        texture.setTextureFromID(boat.texture);
        creativeTab.setSelectedItem(boat.creativeTab);
        maxStackSize.setValue(boat.maxStackSize);
        hasChest.setSelected(boat.hasChest);
        speedMultiplier.setValue(boat.speedMultiplier);
        durability.setValue(boat.durability);
        material.setSelectedItem(boat.material);
        customSound.setSound(boat.customSound);
        buoyancy.setValue(boat.buoyancy);
    }

    @Override
    public ModElementType<?> getModElementType() {
        return ModElementType.CUSTOM;
    }
}
