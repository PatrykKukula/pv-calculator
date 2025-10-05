package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectRequestDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidOwnershipException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.ResourceNotFoundException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.ProjectMapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.MaterialBuilderFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction.ConstructionMaterialBuilder;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ProjectRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Security.UserDetailsServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.ProjectMapper.mapProjectDtoToProject;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.ProjectMapper.mapProjectUpdateDtoToProject;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.ServiceUtils.validateId;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.ServiceUtils.validateSortDirection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MaterialService materialService;
    private final InstallationMaterialRepository installationMaterialRepository;
    private final UserEntityService userService;
    private final MaterialBuilderFactory builderFactory;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void createProject(ProjectDto projectDto){
        UserEntity user = userService.loadCurrentUser();
        Project project = mapProjectDtoToProject(projectDto);
        project.setUser(user);

        Project savedProject = projectRepository.save(project);
        log.info("Project created with ID:{} by user:{} ", savedProject.getProjectId(), user.getUsername());
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<ProjectDto> findAllProjects(ProjectRequestDto projectRequestDto){
       UserEntity user = userService.loadCurrentUser();
       log.info("findAllProjects invoked by user:{} ", user.getUsername());

       String sortDirection = validateSortDirection(projectRequestDto.getSortDirection());
       Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),"createdAt");
       Pageable pageable = PageRequest.of(projectRequestDto.getPageNo(), projectRequestDto.getPageSize(), sort);

       return projectRepository.findAllProjectsByUsername(user.getUsername(), pageable).map(ProjectMapper::mapProjectToProjectDto);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void removeProject(Long projectId){
        validateId(projectId);

        UserEntity user = userService.loadCurrentUser();

        Project project = findProjectById(projectId);

        validateProjectOwner(user, project, "remove");

        projectRepository.delete(project);
        log.info("Project with ID:{} removed successfully", projectId);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Transactional
    public void updateProject(Long projectId, ProjectUpdateDto projectUpdateDto){
        validateId(projectId);

        UserEntity user = userService.loadCurrentUser();

        Project project = findProjectById(projectId);

        validateProjectOwner(user, project, "update");
        Project updatedProject = mapProjectUpdateDtoToProject(projectUpdateDto, project);

        if (projectUpdateDto.getModuleFrame() != null || projectUpdateDto.getModuleWidth() != null || projectUpdateDto.getModuleLength() != null){
            project.getInstallations().forEach(installation -> {
            ConstructionMaterialBuilder builder = builderFactory.createConstructionBuilder(installation, project);

            List<InstallationMaterial> materials = builder.createInstallationConstructionMaterials();
            installationMaterialRepository.removeAllForInstallation(installation.getInstallationId());

            installation.setMaterials(materials);
            });
        }
        projectRepository.save(updatedProject);
        log.info("Project with ID:{} updated successfully", projectId);
    }
    private Project findProjectById(Long projectId){
        return projectRepository.findByProjectIdWithUserAndInstallations(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", projectId));
    }
    private void validateProjectOwner(UserEntity user, Project project, String action){
        if (!project.getUser().getUserId().equals(user.getUserId())) {
            log.info("Attempt to %s project with ID:{} by user:{} . This project doesn't belong to user.".formatted(action)
                    , project.getProjectId(), user.getUsername());
            throw new InvalidOwnershipException("Project", user.getUsername());
        }
    }

}
