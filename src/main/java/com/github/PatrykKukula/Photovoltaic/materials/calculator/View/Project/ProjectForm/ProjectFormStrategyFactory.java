package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;

public class ProjectFormStrategyFactory {
    private final ProjectInterface projectDto;

    public ProjectFormStrategyFactory(ProjectInterface projectDto){
        this.projectDto = projectDto;
    }
    public ProjectFormStrategy<?> getStrategy(){
        if (projectDto instanceof ProjectDto) return new AddProjectFormStrategy();
        else if (projectDto instanceof ProjectUpdateDto) return new UpdateProjectFormStrategy();
        else throw new RuntimeException("Exception during creating ProjectFormStrategy");
    }
}
