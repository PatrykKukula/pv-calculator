package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;

public class ProjectMapper {

    public static Project mapProjectDtoToProject(ProjectDto projectDto){
        return Project.builder()
                .title(projectDto.getTitle())
                .investor(projectDto.getInvestor() != null ? projectDto.getInvestor() : "")
                .country(projectDto.getCountry() != null ? projectDto.getCountry() : "")
                .voivodeship(projectDto.getVoivodeship() != null ? projectDto.getVoivodeship() : "")
                .city(projectDto.getCity() != null ? projectDto.getCity() : "")
                .modulePower(projectDto.getModulePower())
                .moduleLength(projectDto.getModuleLength())
                .moduleWidth(projectDto.getModuleWidth())
                .moduleFrame(projectDto.getModuleFrame())
                .build();
    }
    public static ProjectDto mapProjectToProjectDto(Project project){
        return ProjectDto.builder()
                .title(project.getTitle())
                .investor(project.getInvestor() != null ? project.getInvestor() : "")
                .country(project.getCountry() != null ? project.getCountry() : "")
                .city(project.getCity() != null ? project.getCity() : "")
                .voivodeship(project.getVoivodeship() != null ? project.getVoivodeship() : "")
                .modulePower(project.getModulePower())
                .moduleLength(project.getModuleLength())
                .moduleWidth(project.getModuleWidth())
                .moduleFrame(project.getModuleFrame())
                .build();
    }
    public static Project mapProjectUpdateDtoToProject(ProjectUpdateDto projectUpdateDto, Project project){
        if (projectUpdateDto.getTitle() != null) project.setTitle(projectUpdateDto.getTitle());
        if (projectUpdateDto.getInvestor() != null) project.setInvestor(projectUpdateDto.getInvestor());
        if (projectUpdateDto.getVoivodeship() != null) project.setVoivodeship(projectUpdateDto.getVoivodeship());
        if (projectUpdateDto.getCity() != null) project.setCity(projectUpdateDto.getCity());
        if (projectUpdateDto.getCountry() != null) project.setCountry(projectUpdateDto.getCountry());
        if (projectUpdateDto.getModulePower() != null) project.setModulePower(projectUpdateDto.getModulePower());
        if (projectUpdateDto.getModuleLength() != null) project.setModuleLength(projectUpdateDto.getModuleLength());
        if (projectUpdateDto.getModuleWidth() != null) project.setModuleWidth(projectUpdateDto.getModuleWidth());
        if (projectUpdateDto.getModuleFrame() != null) project.setModuleFrame(projectUpdateDto.getModuleFrame());
        return project;
    }
}
