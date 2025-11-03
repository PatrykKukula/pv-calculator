package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectForm;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.binder.BeanValidationBinder;

public class UpdateProjectFormStrategy implements ProjectFormStrategy<ProjectUpdateDto> {
    @Override
    public String getHeader() {
        return "Fill the form to update project data";
    }
    @Override
    public void save(ProjectService projectService, ProjectInterface projectDto, Long projectId, Long userId) {
        projectService.updateProject(projectId, (ProjectUpdateDto) projectDto);
        UI.getCurrent().navigate(ProjectsView.class, userId);
    }
    @Override
    public Class<ProjectUpdateDto> getProjectClass() {
        return ProjectUpdateDto.class;
    }

    @Override
    public void fillFormFields(BeanValidationBinder<ProjectInterface> binder, ProjectInterface projectInterface) {
        binder.setBean(projectInterface);
    }
}
