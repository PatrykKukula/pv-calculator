package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectRequestDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidOwnershipException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.ResourceNotFoundException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Files.MaterialsExporter;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Construction.ConstructionMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.MaterialBuilderFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.InstallationMaterialMapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.ProjectMapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.UserEntity;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ProjectRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.InstallationMaterialListToMapMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.CacheConstants.*;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ElectricalMaterialConstants.CONVERT_W_TO_KW;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.ProjectMapper.mapProjectDtoToProject;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.ProjectMapper.mapProjectUpdateDtoToProject;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.ServiceUtils.validateId;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.ServiceUtils.validateSortDirection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final InstallationRepository installationRepository;
    private final InstallationMaterialRepository installationMaterialRepository;
    private final MaterialsExporter materialsExporter;
    private final UserEntityService userService;
    private final MaterialBuilderFactory builderFactory;
    private final CacheManager cacheManager;

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

        String sortDirection = validateSortDirection(projectRequestDto.getSortDirection());
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),"createdAt");
        Pageable pageable = PageRequest.of(projectRequestDto.getPageNo(), projectRequestDto.getPageSize(), sort);

        Page<ProjectDto> projects = projectRequestDto.getTitle() == null ? projectRepository.findAllProjectsByUsername(user.getUsername(), pageable).map(ProjectMapper::mapProjectToProjectDto) :
                projectRepository.findAllProjectsByUsernameAndTitle(user.getUsername(), projectRequestDto.getTitle(), pageable).map(ProjectMapper::mapProjectToProjectDto);

        return projects;
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Cacheable(PROJECT_CACHE)
    public ProjectDto findProjectById(Long projectId){
        return ProjectMapper.mapProjectToProjectDto(fetchProjectById(projectId));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ProjectUpdateDto findProjectToUpdateById(Long projectId){
        return ProjectMapper.mapProjectToProjectUpdateDto(fetchProjectById(projectId));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void removeProject(Long projectId){
        validateId(projectId);

        UserEntity user = userService.loadCurrentUser();

        Project project = fetchProjectById(projectId);

        validateProjectOwner(user, project, "remove");

        projectRepository.delete(project);

        Cache projectCache = cacheManager.getCache(PROJECT_CACHE);
        if (projectCache != null) projectCache.evictIfPresent(projectId);

        log.info("Project with ID:{} removed successfully", projectId);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Transactional
    public void updateProject(Long projectId, ProjectUpdateDto projectUpdateDto){
        validateId(projectId);

        UserEntity user = userService.loadCurrentUser();

        Project project = fetchProjectById(projectId);
        ProjectDto projectDto = ProjectMapper.mapProjectToProjectDto(project);

        validateProjectOwner(user, project, "update");

        setConstructionMaterials(projectUpdateDto, project, projectDto);
        setElectricalMaterials(projectUpdateDto, project);

        Project updatedProject = mapProjectUpdateDtoToProject(projectUpdateDto, project);

        projectRepository.save(updatedProject);

        Cache projectCache = cacheManager.getCache(PROJECT_CACHE);
        if (projectCache != null) projectCache.put(projectId, ProjectMapper.mapProjectToProjectDto(updatedProject));

        log.info("Project with ID:{} updated successfully", projectId);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Cacheable(INSTALLATION_COUNT)
    public Integer getInstallationCountForProject(Long projectId){
        return projectRepository.getInstallationNumber(projectId);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Cacheable(PROJECT_POWER)
    public Double getTotalPowerForProject(Long projectId){
        Long modulePower = projectRepository.getModulePowerByProjectId(projectId);
        return (double)projectRepository.getAllModulesByProjectId(projectId) * modulePower / CONVERT_W_TO_KW;
    }
    private void setConstructionMaterials(ProjectUpdateDto projectUpdateDto, Project project, ProjectDto projectDto){
        if (projectUpdateDto.getModuleFrame() != project.getModuleFrame() || projectUpdateDto.getModuleWidth() != project.getModuleWidth() || projectUpdateDto.getModuleLength() != project.getModuleLength()){
            project.getInstallations().forEach(installation -> {
                ConstructionMaterialAssembler assembler = builderFactory.createConstructionAssembler(installation, projectDto);
                List<InstallationMaterial> constructionMaterials = assembler.createInstallationConstructionMaterials();

                installation.getMaterials().removeIf(material -> material.getConstructionMaterial() != null);
                installation.getMaterials().addAll(constructionMaterials);

                Cache cache = cacheManager.getCache(CONSTRUCTION_MATERIALS);
                if (cache != null) cache.put(installation.getInstallationId(), constructionMaterials.stream().map(InstallationMaterialMapper::mapInstallationMaterialToInstallationMaterialDto));
            });
        }
    }
    private void setElectricalMaterials(ProjectUpdateDto projectUpdateDto, Project project){
       if (projectUpdateDto.getModulePower() != project.getModulePower()){
           project.getInstallations().forEach(installation -> {
               ElectricalMaterialAssembler assembler = builderFactory.createElectricalAssembler(installation, projectUpdateDto.getModulePower().longValue());
               List<InstallationMaterial> electricalMaterials = assembler.createInstallationElectricalMaterials();

               installation.getMaterials().removeIf(material -> material.getElectricalMaterial() != null);
               installation.getMaterials().addAll(electricalMaterials);

               Cache cache = cacheManager.getCache(ELECTRICAL_MATERIALS);
               if (cache != null) cache.put(installation.getInstallationId(), electricalMaterials.stream().map(InstallationMaterialMapper::mapInstallationMaterialToInstallationMaterialDto));
           });
       }
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ByteArrayOutputStream exportProjectMaterialsToExcel(Long projectId) throws IOException {
        List<Installation> installations = installationRepository.findAllInstallationsForProject(projectId);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", projectId));
        InstallationMaterialListToMapMapper mapper = new InstallationMaterialListToMapMapper();

        List<InstallationMaterial> electricalMaterials = installationMaterialRepository.fetchElectricalMaterialsForProject(projectId);
        Map<String, Long> electricalMaterialsMap = mapper.createElectricalMaterialsMap(electricalMaterials);

        List<InstallationMaterial> constructionMaterials = installationMaterialRepository.fetchConstructionMaterialsForProject(projectId);
        Map<String, Long> constructionMaterialsMap = mapper.createConstructionMaterialsMap(constructionMaterials);

        Double power = projectRepository.getTotalPowerForProject(projectId);

        return materialsExporter.exportMaterialsToExcelForProject(installations, project, electricalMaterialsMap, constructionMaterialsMap, installations.size(), power);
    }
    private Project fetchProjectById(Long projectId){
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
