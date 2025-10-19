package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidRowQuantityException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.ResourceNotFoundException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.InstallationMapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction.ConstructionMaterialBuilder;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction.ConstructionMaterialCalculator;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.*;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ProjectRepository;
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

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants.PAGE_NO;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants.PAGE_SIZE;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.ServiceUtils.validateId;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstallationService {
    private final InstallationRepository installationRepository;
    private final InstallationMaterialRepository installationMaterialRepository;
    private final ProjectRepository projectRepository;
    private final MaterialService constructionMaterialService;
    private final UserEntityService userService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Transactional
    public Installation createInstallation(InstallationDto installationDto, ProjectDto projectDto){
        Installation installation = InstallationMapper.mapInstallationDtoToInstallation(installationDto);
        validateRowsQuantity(installation.getRows());
        UserEntity user = userService.loadCurrentUser();

        ConstructionMaterialCalculator constructionMaterialCalculator = new ConstructionMaterialCalculator(constructionMaterialService, installation, projectDto);
        ConstructionMaterialBuilder builder = new ConstructionMaterialBuilder(installation, constructionMaterialService, constructionMaterialCalculator, projectDto);

        List<InstallationMaterial> materials = builder.createInstallationConstructionMaterials();

        installation.setMaterials(materials);

        Project project = projectRepository.findById(projectDto.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project", projectDto.getProjectId()));
        installation.setProject(project);

        Installation createdInstallation = installationRepository.save(installation);
        log.info("New installation with ID:{} created by user: {}", createdInstallation.getInstallationId(), user.getUsername());

        return createdInstallation;
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Transactional
    public void removeInstallation(Long installationId){
        validateId(installationId);

        Installation installation = installationRepository.findById(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));

        installationRepository.delete(installation);
        log.info("Installation with ID:{} removed successfully", installationId);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Transactional
    public Installation updateInstallation(Long installationId, InstallationUpdateDto installationUpdateDto, ProjectDto project){
        validateId(installationId);

        Installation installation = installationRepository.findByIdWithRowsAndProject(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));
        Installation updatedInstallation = InstallationMapper.mapInstallationUpdateDtoToInstallation(installationUpdateDto, installation);

        ConstructionMaterialCalculator constructionMaterialCalculator = new ConstructionMaterialCalculator(constructionMaterialService, updatedInstallation, project);
        ConstructionMaterialBuilder builder = new ConstructionMaterialBuilder(updatedInstallation, constructionMaterialService, constructionMaterialCalculator, project);

        List<InstallationMaterial> materials = builder.createInstallationConstructionMaterials();


        installation.getMaterials().clear();
        installation.getMaterials().addAll(materials);

        Installation savedInstallation = installationRepository.save(updatedInstallation);
        log.info("Installation with ID:{} updated successfully", installationId);

        return savedInstallation;
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<InstallationDto> findAllInstallationsForProject(Long projectId){
        validateId(projectId);

        UserEntity user = userService.loadCurrentUser();
        log.info("Invoking findAllInstallationsForProject by user:{}. ID validated successfully.", user.getUsername());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(PAGE_NO, PAGE_SIZE, sort);

        return installationRepository.findAllInstallationsByProjectId(projectId, pageable).map(InstallationMapper::mapInstallationToInstallationDto);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public InstallationDto findInstallationById(Long installationId){
        Installation installation = installationRepository.findByIdWithRowsAndProject(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));
        return InstallationMapper.mapInstallationToInstallationDto(installation);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public InstallationUpdateDto findInstallationUpdateById(Long installationId){
        Installation installation = installationRepository.findByIdWithRowsAndProject(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));
        return InstallationMapper.mapInstallationToInstallationUpdateDto(installation);
    }
    private void validateRowsQuantity(List<Row> rows){
        for (Row row : rows){
            if (row.getModuleQuantity() < 5 ) {
               throw new InvalidRowQuantityException(row.getModuleQuantity());
            }
        }
    }
}
