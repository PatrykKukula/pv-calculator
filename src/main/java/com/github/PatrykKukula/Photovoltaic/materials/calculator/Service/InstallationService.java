package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.ResourceNotFoundException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.InstallationMapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction.ConstructionMaterialBuilder;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction.ConstructionMaterialCalculator;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.*;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.*;
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
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants.PAGE_NO;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants.PAGE_SIZE;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.ServiceUtils.validateId;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstallationService {
    private final InstallationRepository installationRepository;
    private final InstallationMaterialRepository installationMaterialRepository;
    private final MaterialService constructionMaterialService;
    private final UserEntityService userService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN)")
    @Transactional
    public boolean createInstallation(InstallationDto installationDto, Project project){
        Installation installation = InstallationMapper.mapInstallationDtoToInstallation(installationDto);

        UserEntity user = userService.loadCurrentUser();

        ConstructionMaterialCalculator constructionMaterialCalculator = new ConstructionMaterialCalculator(constructionMaterialService, installation, project);
        ConstructionMaterialBuilder builder = new ConstructionMaterialBuilder(installation, constructionMaterialService, constructionMaterialCalculator, project);

        List<InstallationMaterial> materials = builder.createInstallationConstructionMaterials();

        installation.setMaterials(materials);

        Installation createdInstallation = installationRepository.save(installation);
        log.info("New installation with ID:{} created by user: {}", createdInstallation.getInstallationId(), user.getUsername());
        return true;
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void removeInstallation(Long installationId){
        validateId(installationId);

        Installation installation = installationRepository.findById(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));

        installationRepository.delete(installation);
        log.info("Installation with ID:{} removed successfully", installationId);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Transactional
    public void updateInstallation(Long installationId, InstallationUpdateDto installationUpdateDto, Project project){
        validateId(installationId);

        Installation installation = installationRepository.findById(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));
        Installation updatedInstallation = InstallationMapper.mapInstallationUpdateDtoToInstallation(installationUpdateDto, installation);

        ConstructionMaterialCalculator constructionMaterialCalculator = new ConstructionMaterialCalculator(constructionMaterialService, updatedInstallation, project);
        ConstructionMaterialBuilder builder = new ConstructionMaterialBuilder(updatedInstallation, constructionMaterialService, constructionMaterialCalculator, project);

        List<InstallationMaterial> materials = builder.createInstallationConstructionMaterials();

        installationMaterialRepository.removeAllForInstallation(installation.getInstallationId());

        installation.setMaterials(materials);

        installationRepository.save(updatedInstallation);
        log.info("Installation with ID:{} updated successfully", installationId);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN)")
    public Page<InstallationDto> findAllInstallationsForProject(Long projectId){
        validateId(projectId);

        UserEntity user = userService.loadCurrentUser();
        log.info("Invoking findAllInstallationsForProject by user:{}. ID validated successfully.", user.getUsername());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(PAGE_NO, PAGE_SIZE, sort);

        return installationRepository.findAllInstallationsByProjectId(projectId, pageable).map(InstallationMapper::mapInstallationToInstallationDto);
    }
}
