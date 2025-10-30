package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PhaseNumber;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationsRequestDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidRowQuantityException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.PowerOutOfBoundsException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.ResourceNotFoundException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Construction.ConstructionMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.MaterialBuilderFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper.InstallationMapper;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.*;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.CacheConstants.*;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ElectricalMaterialConstants.*;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants.PAGE_NO;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants.PAGE_SIZE;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils.ServiceUtils.validateId;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstallationService {
    private final InstallationRepository installationRepository;
    private final MaterialBuilderFactory factory;
    private final ProjectRepository projectRepository;
    private final UserEntityService userService;
    private final CacheManager cacheManager;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = PROJECT_POWER, key = "#a1.projectId"),
                    @CacheEvict(value = INSTALLATION_COUNT, key = "#a1.projectId")
            }
    )
    public Installation createInstallation(InstallationDto installationDto, ProjectDto projectDto){
        Installation installation = InstallationMapper.mapInstallationDtoToInstallation(installationDto);
        validateRowsQuantity(installation.getRows());
        UserEntity user = userService.loadCurrentUser();
        validateTotalPower(installationDto, projectDto.getModulePower().longValue(), user.getUsername());
        setMaterials(installation, projectDto);

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

        Cache installationCache = cacheManager.getCache(INSTALLATION_CACHE);
        if (installationCache != null) installationCache.evictIfPresent(installationId);

        log.info("Installation with ID:{} removed successfully", installationId);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = PROJECT_POWER, key = "#a1.projectId"),
                    @CacheEvict(cacheNames = {CONSTRUCTION_MATERIALS, ELECTRICAL_MATERIALS}, key = "#installationId")
            }
    )
    public Installation updateInstallation(Long installationId, InstallationUpdateDto installationUpdateDto, ProjectDto project){
        validateId(installationId);
        UserEntity user = userService.loadCurrentUser();
        validateTotalPower(installationUpdateDto, project.getModulePower().longValue(), user.getUsername());

        Installation installation = installationRepository.findByIdWithRowsAndProject(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));
        Installation updatedInstallation = InstallationMapper.mapInstallationUpdateDtoToInstallation(installationUpdateDto, installation);

        setMaterials(installation, project);

        Installation savedInstallation = installationRepository.save(updatedInstallation);
        log.info("Installation with ID:{} updated successfully", installationId);

        Cache cache = cacheManager.getCache(INSTALLATION_CACHE);
        if (cache != null) cache.put(installationId, InstallationMapper.mapInstallationToInstallationDto(savedInstallation));

        return savedInstallation;
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<InstallationDto> findAllInstallationsForProject(Long projectId, InstallationsRequestDto requestDto){
        validateId(projectId);

        UserEntity user = userService.loadCurrentUser();
        log.info("Invoking findAllInstallationsForProject by user:{}. ID validated successfully.", user.getUsername());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(requestDto.getPageNo(), requestDto.getPageSize(), sort);

        return installationRepository.findAllInstallationsByProjectId(projectId, pageable).map(InstallationMapper::mapInstallationToInstallationDto);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Cacheable(INSTALLATION_CACHE)
    public InstallationDto findInstallationById(Long installationId){
        Installation installation = installationRepository.findByIdWithRowsAndProject(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));
        return InstallationMapper.mapInstallationToInstallationDto(installation);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public InstallationUpdateDto findInstallationUpdateById(Long installationId){
        Installation installation = installationRepository.findByIdWithRowsAndProject(installationId).orElseThrow(() -> new ResourceNotFoundException("Installation", installationId));
        return InstallationMapper.mapInstallationToInstallationUpdateDto(installation);
    }
    private void setMaterials(Installation installation, ProjectDto projectDto){
        log.info("Invoking set materials with strings:{} ", installation.getStrings());
        ConstructionMaterialAssembler constructionAssembler = factory.createConstructionAssembler(installation, projectDto);
        List<InstallationMaterial> constructionMaterials = constructionAssembler.createInstallationConstructionMaterials();

        ElectricalMaterialAssembler electricalAssembler = factory.createElectricalAssembler(installation, projectDto.getModulePower().longValue());
        List<InstallationMaterial> electricalMaterials = electricalAssembler.createInstallationElectricalMaterials();

        installation.getMaterials().clear();
        installation.getMaterials().addAll(constructionMaterials);
        installation.getMaterials().addAll(electricalMaterials);
        log.info("Finished set materials");
    }
    private void validateRowsQuantity(List<Row> rows){
        for (Row row : rows){
            if (row.getModuleQuantity() < 5 ) {
               throw new InvalidRowQuantityException(row.getModuleQuantity());
            }
        }
    }
    private void validateTotalPower(InstallationInterface installationDto, Long modulePower, String username){
        double totalPower = (installationDto.getRows().stream().mapToDouble(row -> row.getModuleQuantity() * modulePower).sum() / CONVERT_W_TO_KW);
        if (totalPower > Double.parseDouble(ALLOWED_3_PHASED_POWER)){
            throw new PowerOutOfBoundsException(ALLOWED_3_PHASED_POWER, String.valueOf(totalPower), username);
        } else if (installationDto.getPhaseNumber() == PhaseNumber.SINGLE_PHASE && totalPower > Double.parseDouble(ALLOWED_1_PHASED_POWER)) {
            throw new PowerOutOfBoundsException(ALLOWED_1_PHASED_POWER, String.valueOf(totalPower), username);
        }
    }
}
