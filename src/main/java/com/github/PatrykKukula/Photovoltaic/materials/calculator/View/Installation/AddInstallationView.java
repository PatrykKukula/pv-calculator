package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.RowDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.AppException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidIdException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.ResourceNotFoundException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectView;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components.NotificationComponents.showErrorMessage;
import static org.reflections.Reflections.log;

@Route("add-installation")
@PageTitle("PV calculator - add installation")
@RolesAllowed({"USER", "ADMIN"})
@RequiredArgsConstructor
@CssImport("./styles/common-styles.css")
public class AddInstallationView extends FormLayout implements HasUrlParameter<Long> {
    private final InstallationService installationService;
    private final ProjectService projectService;
    private ProjectDto project;
    private final BeanValidationBinder<InstallationDto> binder = new BeanValidationBinder<>(InstallationDto.class);
    private final InstallationDto installationDto = new InstallationDto();
    private final List<IntegerField> rowsFields = new ArrayList<>();
    private Integer rowSpanIndex = 7;
    private Long rowNo = 1L;

    @Override
    public void setParameter(BeforeEvent event, Long projectId) {
        getStyle().set("width", "20%").set("height", "100%").set("align-items", "top").set("margin", "auto");
            setResponsiveSteps(new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP));
            try {
                project = projectService.findProjectById(projectId);
                IntegerField rowField = rowField();
                rowField.getStyle().set("width", "40%").set("margin", "0");

                add(header(), addressField(), installationTypeComboBox(), moduleOrientationComboBox(), acCableField(), dcCableField(), rowField, addRowSpan(), saveInstallationButton(projectId)
                        , cancelButton(projectId));
            }
            catch (InvalidIdException ex){
                showErrorMessage(ex.getMessage());
            }
            catch (ResourceNotFoundException ex){
                log.info(ex.getMessage());
                showErrorMessage(ex.getUserMessage());
            }
            binder.setBean(installationDto);
    }
    /**
     * Saves installation after successful validation. If validation fails error will be displayed under
     * each field, or as a notification.
     * @return Button to save installation
     */
    private Button saveInstallationButton(Long projectId){
        Button button = new Button("Save installation");
        button.addClickListener(e -> {
            if (validateForm()){
                InstallationDto installationDtoBean = prepareInstallation();
                    Installation created = installationService.createInstallation(installationDtoBean, project);
                    log.info("Installation created:{} ", created);
                    if (created != null) UI.getCurrent().navigate(ProjectView.class, projectId);
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
    private InstallationDto prepareInstallation(){
        InstallationDto dto = binder.getBean();
        List<RowDto> rows = new ArrayList<>();

        for (int i = 0; i < rowsFields.size(); i++) {
            rows.add(new RowDto(
                    (long) (i + 1),
                    rowsFields.get(i).getValue().longValue()
            ));
        }
        dto.setRows(rows);
        return dto;
    }
    private Span addRowSpan(){
        Span span = new Span(VaadinIcon.PLUS.create(), new Span("Add row"));
        span.getStyle().set("cursor", "pointer").set("padding", "10px 0");
        span.addClickListener(e -> {
            rowNo++;
            IntegerField moduleQuantity = rowField();
            Span moduleQuantitySpan = new Span(moduleQuantity);
            moduleQuantitySpan.getElement().setAttribute("row-number", String.valueOf(rowNo-1));
            Icon removeRow = removeRowIcon(moduleQuantitySpan);
            moduleQuantitySpan.add(removeRow);
            moduleQuantitySpan.getStyle().set("width", "45%").set("align-self", "center");
            addComponentAtIndex(rowSpanIndex, moduleQuantitySpan);
            rowSpanIndex++;
        });
        return span;
    }
    private Icon removeRowIcon(Span span){
        Icon removeRow = VaadinIcon.MINUS.create();
        removeRow.getStyle().set("color", "red").set("font-size", "10px").set("margin-left", "10px");
        removeRow.addClickListener(e -> {
            remove(span);
            rowsFields.remove(Integer.parseInt(span.getElement().getAttribute("row-number")));
            rowNo--;
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
                .withValidator(new BeanValidator(InstallationDto.class, "installationType"))
                .bind("installationType");
        return comboBox;
    }
    private ComboBox<ModuleOrientation> moduleOrientationComboBox(){
        ComboBox<ModuleOrientation> comboBox = new ComboBox<>("Module orientation");
        comboBox.getStyle().set("width", "40%");
        comboBox.setItems(ModuleOrientation.values());
        comboBox.setItemLabelGenerator(moduleOrientationLabelGenerator());
        binder.forField(comboBox)
                .withValidator(new BeanValidator(InstallationDto.class, "moduleOrientation"))
                .bind("moduleOrientation");
        return comboBox;
    }
    private TextField addressField(){
        TextField textField = new TextField("address");
        binder.forField(textField).withValidator(new BeanValidator(InstallationDto.class, "address"))
                .bind("address");
        textField.getStyle().set("width", "40%");
        return textField;
    }
    private IntegerField acCableField(){
        IntegerField acCableField = integerField("AC cable length");
        binder.forField(acCableField)
                .withValidator(new BeanValidator(InstallationDto.class, "acCableLength"))
                .bind("acCableLength");
        return acCableField;
    }
    private IntegerField dcCableField(){
        IntegerField dcCableField = integerField("DC cable length");
        binder.forField(dcCableField)
                .withValidator(new BeanValidator(InstallationDto.class, "dcCableLength"))
                .bind("dcCableLength");
        return dcCableField;
    }
    private IntegerField rowField(){
        IntegerField integerField = integerField("Row %s - Module quantity".formatted(rowNo));
        integerField.setMin(5);
        integerField.setMax(24);
        integerField.setRequired(true);
        integerField.setRequiredIndicatorVisible(true);
        IntegerField.IntegerFieldI18n i18n = new IntegerField.IntegerFieldI18n();
        i18n.setMinErrorMessage("Row should have at least 5 modules"); //it is due to the starting voltage of the inverter
        i18n.setMaxErrorMessage("Row should not exceed 24 modules"); //it is due to the max system voltage
        integerField.setI18n(i18n);
        integerField.getStyle().set("width", "90%").set("margin-left", "16px");
        rowsFields.add(integerField);
        return integerField;
    }
    private IntegerField integerField(String label){
        IntegerField integerField = new IntegerField(label);
        integerField.getStyle().set("width", "40%");
        return integerField;
    }
    private Div header(){
        Div div = new Div("Fill up the form to add installation");
        div.getStyle().set("font-size", "24px").set("font-family", "georgia");
        return div;
    }
    private Button cancelButton(Long projectId){
        Button button = new Button("Cancel");
        button.addClickListener(e -> {
           UI.getCurrent().navigate(ProjectView.class, projectId);
        });
        return button;
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
