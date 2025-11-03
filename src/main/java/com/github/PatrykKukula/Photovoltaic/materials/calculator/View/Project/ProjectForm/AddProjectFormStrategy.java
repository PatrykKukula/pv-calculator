package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectForm;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AddProjectFormStrategy implements ProjectFormStrategy<ProjectDto> {
    @Override
    public String getHeader() {
        return "Fill the form to add project";
    }
    @Override
    public void save(ProjectService projectService, ProjectInterface projectDto, Long projectId, Long userId) {
        projectService.createProject((ProjectDto) projectDto);
        UI.getCurrent().navigate(ProjectsView.class, userId);
    }
    @Override
    public Class<ProjectDto> getProjectClass() {
        return ProjectDto.class;
    }
    @Override
    public void fillFormFields(BeanValidationBinder<ProjectInterface> binder, ProjectInterface projectInterface) {
        binder.setBean(projectInterface);
    }
}
