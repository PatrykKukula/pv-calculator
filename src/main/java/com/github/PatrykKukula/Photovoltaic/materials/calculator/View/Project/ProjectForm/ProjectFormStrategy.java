package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.vaadin.flow.data.binder.BeanValidationBinder;

public interface ProjectFormStrategy<T extends ProjectInterface> {
    String getHeader();
    void save(ProjectService projectService, ProjectInterface projectDto, Long projectId, Long userId);
    Class<T> getProjectClass();
    void fillFormFields(BeanValidationBinder<ProjectInterface> binder, ProjectInterface projectInterface);
}
