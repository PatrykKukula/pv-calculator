package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionMaterialConstants;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.MainView;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.validator.BeanValidator;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class  ProjectFormLayout<T extends ProjectInterface> extends VerticalLayout {
    private final ProjectService projectService;
    private final BeanValidationBinder<ProjectInterface> binder = new BeanValidationBinder<>(ProjectInterface.class);
    private final ProjectInterface projectDto;
    private ProjectUpdateDto project;
    private final Long projectId;
    private final ProjectFormStrategy<?> strategy;
    private final ProjectFormStrategyFactory projectFormStrategyFactory;

    public ProjectFormLayout (ProjectService projectService, T projectDto, Long projectId){
        this.projectService = projectService;
        this.projectDto = projectDto;
        this.projectId = projectId;

        projectFormStrategyFactory = new ProjectFormStrategyFactory(projectDto);
        strategy = projectFormStrategyFactory.getStrategy();

        getStyle().set("align-items", "center");
    }
    public void configureFormLayout(Long userId){
        TextField titleField = addTextField("Title", "title");
        TextField investorField = addTextField("Investor", "investor");
        TextField countryField = addTextField("Country", "country");
        TextField voivodeshipField = addTextField("Voivodeship", "voivodeship");
        TextField cityField = addTextField("City", "city");
        IntegerField modulePowerField = addIntegerField("Module power [W]", "modulePower");
        IntegerField moduleLengthField = addIntegerField("Module length [mm]", "moduleLength");
        IntegerField moduleWidthField = addIntegerField("Module width [mm]", "moduleWidth");
        IntegerField moduleFrameField = addIntegerField("Module frame thickness [mm]", "moduleFrame");

        binder.forField(moduleFrameField).withValidator(
                        new Validator<Integer>() {
                            @Override
                            public ValidationResult apply(Integer value, ValueContext context) {
                                if (value == null) return ValidationResult.create("Valid module frame is " + ConstructionMaterialConstants.VALID_FRAME,
                                        ErrorLevel.ERROR);
                                if(value == 30 || value == 35 || value == 40) return ValidationResult.ok();
                                return ValidationResult.create("Valid module frame is " + ConstructionMaterialConstants.VALID_FRAME,
                                        ErrorLevel.ERROR);
                            }
                        })
                .bind("moduleFrame");
        strategy.fillFormFields(binder, projectDto);

        Button saveButton = saveButton(userId);
        Button cancelButton = cancelButton();
        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonsLayout.getStyle().set("justify-content", "space-between").set("width", "57%");

        Div upperDiv = setUpperDiv();

        add(upperDiv, titleField, investorField, countryField, voivodeshipField, cityField, modulePowerField, moduleLengthField,
                moduleWidthField, moduleFrameField, buttonsLayout);
    }
    private Button saveButton(Long userId){
        Button saveButton = new Button("Save project");
        saveButton.addClickListener(e -> {
            if (binder.isValid()) {
                ProjectInterface projectDtoBean = binder.getBean();
                strategy.save(projectService, projectDtoBean, projectId, userId);
            }
            else {
                binder.validate();
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.getStyle().set("width", "70%");
        return saveButton;
    }
    private Div setUpperDiv(){
        Div div = new Div(strategy.getHeader());
        div.getStyle().set("font-size", "24px").set("font-family", "Georgia").set("text-align", "center").set("padding", "20px 0px");

        return div;
    }
    private TextField addTextField(String label, String field){
        TextField textField = new TextField(label);
        binder.forField(textField)
                .withValidator(new BeanValidator(strategy.getProjectClass(), field))
                .bind(field);
        textField.setWidth("400px");
        return textField;
    }
    private IntegerField addIntegerField(String label, String field){
        IntegerField integerField = new IntegerField(label);
        binder.forField(integerField)
                .withValidator(new BeanValidator(strategy.getProjectClass(), field))
                .bind(field);
        integerField.setWidth("400px");
        return integerField;
    }
    private Button cancelButton(){
        Button cancelButton = new Button("cancel");
        cancelButton.addClickListener(e -> {
            UI.getCurrent().navigate(MainView.class);
        });
        cancelButton.getStyle().set("width", "25%");
        return cancelButton;
    }
}

