package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PhaseNumber;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.RowDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationView;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.NotificationComponents.showErrorMessage;

@Slf4j
public class InstallationFormLayout<T extends InstallationInterface> extends VerticalLayout {
    private final InstallationService installationService;
    private final T installationInterface;
    private final InstallationFormStrategy<T> strategy;
    private final List<IntegerField> rowsFields = new ArrayList<>();
    private final BeanValidationBinder<T> binder;
    private final InstallationFormStrategyFactory factory;
    private final ProjectDto projectDto;
    private Integer rowSpanIndex = 10;
    private Long rowNo = 1L;

    public InstallationFormLayout(T installationInterface, InstallationService installationService, ProjectDto projectDto, Long projectId){
        this.installationService = installationService;
        this.installationInterface = installationInterface;
        this.projectDto = projectDto;

        getStyle().set("width", "40%").set("height", "100%").set("align-items", "center").set("margin", "auto");

        factory = new InstallationFormStrategyFactory();
        strategy = factory.installationFormStrategy(installationInterface);

        binder = new BeanValidationBinder<>(strategy.getDtoClass());

        Div header = header();
        TextField addressField = addressField();
        ComboBox<ConstructionType> constructionTypeComboBox = installationTypeComboBox();
        ComboBox<ModuleOrientation> moduleOrientationComboBox = moduleOrientationComboBox();
        ComboBox<PhaseNumber> phaseNumberComboBox = phaseNumbernComboBox();
        ComboBox<Boolean> lightingProtectionComboBox = lightingProtectionComboBox();
        IntegerField acCableField = acCableField();
        IntegerField dcCableField = dcCableField();
        IntegerField lgyCableField = lgyCableField();
        IntegerField stringsField = stringsField();
        Span addRowSpan = addRowSpan(null);
        Button saveButton = saveInstallationButton(installationInterface.getInstallationId());
        Button cancelButton = cancelButton(strategy.getDtoClass() == InstallationDto.class ? projectId : installationInterface.getInstallationId());

        binder.setBean(installationInterface);
        add(header, addressField, constructionTypeComboBox, moduleOrientationComboBox, phaseNumberComboBox, lightingProtectionComboBox,
                acCableField, dcCableField, lgyCableField, stringsField, saveButton, cancelButton);
        setExistingRows(installationInterface.getRows());
        addComponentAtIndex(rowSpanIndex, addRowSpan);

    }
    /**
     * Saves installation after successful validation. If validation fails error will be displayed under
     * each field, or as a notification.
     * @return Button to save installation
     */
    private Button saveInstallationButton(Long installationId){
        Button button = new Button("Save installation");
        button.addClickListener(e -> {
            if (validateForm()){
                InstallationInterface dto = prepareInstallation();
                strategy.save(installationService, dto, projectDto, installationId);
            }
            else {
                binder.validate();
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }
    private boolean validateForm(){
        if (!binder.isValid()){
            binder.validate();
            return false;
        }
        for (int i = 0; i < rowsFields.size(); i++){
            IntegerField integerField = rowsFields.get(i);
            if (integerField.getValue() == null) {
                showErrorMessage("Specify module quantity for row number " + (i+1));
                return false;
            }
        }
        return true;
    }
    private InstallationInterface prepareInstallation(){
        InstallationInterface dto = binder.getBean();
        List<RowDto> rows = new ArrayList<>();
        int iterations = rowsFields.size();
        for (int i = 0; i < iterations; i++) {
            rows.add(new RowDto(
                    (long) (i + 1),
                    rowsFields.get(i).getValue().longValue()
            ));
        }
        dto.setRows(rows);
        return dto;
    }
    private void setExistingRows(List<RowDto> rows){
        if (rows == null || rows.isEmpty()){
            IntegerField rowField = rowField();
            addComponentAtIndex(rowSpanIndex, rowField);
            rowSpanIndex++;
        } else if (rows.size() == 1) {
            IntegerField rowField = rowField();
            rowField.setValue(rows.getFirst().getModuleQuantity().intValue());
            addComponentAtIndex(rowSpanIndex, rowField);
            rowSpanIndex++;
        } else {
            for (RowDto row : rows) {
                IntegerField rowField = rowField();
                if (row.getRowNumber() == 1) {
                    rowField.setValue(row.getModuleQuantity().intValue());
                    addComponentAtIndex(rowSpanIndex, rowField);
                    rowSpanIndex++;
                }
                else {
                    rowField.getStyle().set("width", "100%");
                    rowField.setValue(row.getModuleQuantity().intValue());
                    addRowLayout(rowField);
                }
            }
        }
    }
    private Span addRowSpan(RowDto row){
        Span span = new Span(VaadinIcon.PLUS.create(), new Span("Add row"));
        span.getStyle().set("cursor", "pointer").set("padding", "10px 0");
        span.addClickListener(e -> {
            IntegerField moduleQuantity = rowField();
            if (row != null) moduleQuantity.setValue(row.getModuleQuantity().intValue());
            moduleQuantity.getStyle().set("width", "100%");
            addRowLayout(moduleQuantity);
        });
        return span;
    }
    private void addRowLayout(IntegerField moduleQuantity){
        HorizontalLayout rowLayout = new HorizontalLayout(moduleQuantity);

        Icon removeRow = removeRowIcon(rowLayout, moduleQuantity);
        rowLayout.add(removeRow);
        rowLayout.setAlignItems(Alignment.CENTER);
        rowLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        rowLayout.setWidth("40%");
        addComponentAtIndex(rowSpanIndex, rowLayout);

        rowSpanIndex++;
    }
    private Icon removeRowIcon(HorizontalLayout layout, IntegerField rowField){
        Icon removeRow = VaadinIcon.MINUS.create();
        removeRow.getStyle().set("color", "red").set("font-size", "10px").set("margin-left", "10px");
        removeRow.addClickListener(e -> {
            remove(layout);
            rowsFields.remove(Integer.parseInt(rowField.getElement().getAttribute("row-number")));
            reApplyRowNumber();

            rowSpanIndex--;
        });
        return removeRow;
    }
    private ComboBox<ConstructionType> installationTypeComboBox(){
        ComboBox<ConstructionType> comboBox = new ComboBox<>("Installation type");
        comboBox.getStyle().set("width", "40%");
        comboBox.setItems(ConstructionType.values());
        comboBox.setItemLabelGenerator(constructionTypeLabelGenerator());
        binder.forField(comboBox)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "constructionType"))
                .bind("constructionType");
        return comboBox;
    }
    private ComboBox<ModuleOrientation> moduleOrientationComboBox(){
        ComboBox<ModuleOrientation> comboBox = new ComboBox<>("Module orientation");
        comboBox.getStyle().set("width", "40%");
        comboBox.setItems(ModuleOrientation.values());
        comboBox.setItemLabelGenerator(moduleOrientationLabelGenerator());
        binder.forField(comboBox)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "moduleOrientation"))
                .bind("moduleOrientation");
        return comboBox;
    }
    private ComboBox<PhaseNumber> phaseNumbernComboBox(){
        ComboBox<PhaseNumber> comboBox = new ComboBox<>("Phase number:");
        comboBox.getStyle().set("width", "40%");
        comboBox.setItems(PhaseNumber.values());
        comboBox.setItemLabelGenerator(item -> item.name().toLowerCase());
        binder.forField(comboBox)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "phaseNumber"))
                .bind("phaseNumber");
        return comboBox;
    }
    private ComboBox<Boolean> lightingProtectionComboBox(){
        ComboBox<Boolean> comboBox = new ComboBox<>("Lighting protection in object");
        comboBox.getStyle().set("width", "40%");
        comboBox.setItems(true, false);
        comboBox.setItemLabelGenerator(item -> item ? "Present" : "Not present");
        binder.forField(comboBox)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "lightingProtection"))
                .bind("lightingProtection");
        return comboBox;
    }
    private TextField addressField(){
        TextField textField = new TextField("Address");
        binder.forField(textField)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "address"))
                .bind("address");
        textField.getStyle().set("width", "40%");
        return textField;
    }
    private IntegerField acCableField(){
        IntegerField acCableField = integerField("AC cable length");
        binder.forField(acCableField)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "acCableLength"))
                .bind("acCableLength");
        acCableField.setHelperText("from inverter to AC switchboard");
        return acCableField;
    }
    private IntegerField dcCableField(){
        IntegerField dcCableField = integerField("DC cable length");
        binder.forField(dcCableField)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "dcCableLength"))
                .bind("dcCableLength");
        dcCableField.setHelperText("from inverter to first row of modules");
        return dcCableField;
    }
    private IntegerField lgyCableField(){
        IntegerField lgyCableField = integerField("LgY cable length");
        binder.forField(lgyCableField)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "lgyCableLength"))
                .bind("lgyCableLength");
        lgyCableField.setHelperText("from inverter to first row of modules");
        return lgyCableField;
    }
    private IntegerField stringsField(){
        IntegerField stringsField = integerField("Number of strings");
        binder.forField(stringsField)
                .withValidator(new BeanValidator(strategy.getDtoClass(), "strings"))
                .bind("strings");
        return stringsField;
    }
    private IntegerField rowField(){
        IntegerField integerField = integerField("Row %s - Module quantity".formatted(rowNo));
        integerField.getElement().setAttribute("row-number", String.valueOf(rowNo-1));
        rowNo++;
        integerField.setMin(5);
        integerField.setMax(24);
        integerField.setRequired(true);
        integerField.setRequiredIndicatorVisible(true);
        IntegerField.IntegerFieldI18n i18n = new IntegerField.IntegerFieldI18n();
        i18n.setMinErrorMessage("Row should have at least 5 modules"); //it is due to the starting voltage of the inverter
        i18n.setMaxErrorMessage("Row should not exceed 24 modules"); //it is due to the max system voltage
        integerField.setI18n(i18n);
        integerField.getStyle().set("width", "40%");
        rowsFields.add(integerField);
        return integerField;
    }
    private IntegerField integerField(String label){
        IntegerField integerField = new IntegerField(label);
        integerField.getStyle().set("width", "40%");
        return integerField;
    }
    private Button cancelButton(Long id){
        Button button = new Button("Cancel");
        button.addClickListener(e -> {
            strategy.cancel(id);
        });
        return button;
    }
    private Div header(){
        Div div = new Div(strategy.getTitle());
        div.getStyle().set("font-size", "24px").set("font-family", "georgia");
        return div;
    }
    private void reApplyRowNumber(){
        rowNo = 1L;
        for (IntegerField row : rowsFields){
            row.getElement().removeAttribute("row-number");
            row.getElement().setAttribute("row-number", String.valueOf(rowNo-1));
            rowNo++;
        }
    }
    private ItemLabelGenerator<ConstructionType> constructionTypeLabelGenerator(){
        return item -> {
            String value;
            value = item.name().charAt(0) + item.name().substring(1).toLowerCase();
            return value.replaceAll("_", " ");
        };
    }
    private ItemLabelGenerator<ModuleOrientation> moduleOrientationLabelGenerator(){
        return item -> {
            String value;
            value = item.name().charAt(0) + item.name().substring(1).toLowerCase();
            return value.replaceAll("_", " ");
        };
    }
}

