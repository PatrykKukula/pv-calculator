package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;

import java.time.format.DateTimeFormatter;

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
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .investor(project.getInvestor() != null ? project.getInvestor() : "")
                .country(project.getCountry() != null ? project.getCountry() : "")
                .city(project.getCity() != null ? project.getCity() : "")
                .voivodeship(project.getVoivodeship() != null ? project.getVoivodeship() : "")
                .modulePower(project.getModulePower())
                .moduleLength(project.getModuleLength())
                .moduleWidth(project.getModuleWidth())
                .moduleFrame(project.getModuleFrame())
                .createdAt(project.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .installations(0)
                .build();
    }
    public static ProjectUpdateDto mapProjectToProjectUpdateDto(Project project){
        return ProjectUpdateDto.builder()
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
        project.setTitle(projectUpdateDto.getTitle());
        project.setInvestor(projectUpdateDto.getInvestor());
        project.setVoivodeship(projectUpdateDto.getVoivodeship());
        project.setCity(projectUpdateDto.getCity());
        project.setCountry(projectUpdateDto.getCountry());
        project.setModulePower(projectUpdateDto.getModulePower());
        project.setModuleLength(projectUpdateDto.getModuleLength());
        project.setModuleWidth(projectUpdateDto.getModuleWidth());
        project.setModuleFrame(projectUpdateDto.getModuleFrame());
        return project;
    }
}
