package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationsRequestDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.RowDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidIdException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidRowQuantityException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.PowerOutOfBoundsException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.ResourceNotFoundException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Files.MaterialsExporter;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Construction.ConstructionMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.MaterialBuilderFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.*;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstallationServiceTest {
    @Mock
    private InstallationRepository installationRepository;
    @Mock
    private UserEntityService userEntityService;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private MaterialBuilderFactory factory;
    @Mock
    private ConstructionMaterialAssembler constructionMaterialAssembler;
    @Mock
    private ElectricalMaterialAssembler electricalMaterialAssembler;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private MaterialsExporter materialsExporter;
    @Mock
    private InstallationMaterialRepository installationMaterialRepository;
    @InjectMocks
    private InstallationService installationService;
    private InstallationDto installationDto;
    private Installation installation;
    private InstallationUpdateDto installationUpdateDto;
    private ProjectDto projectDto;
    private Project project;
    private RowDto rowDto;
    private List<RowDto> rows = new ArrayList<>();
    private UserEntity user;
    private InstallationMaterial constructionMaterial;
    private InstallationMaterial electricalMaterial;
    private List<InstallationMaterial> constructionMaterials = new ArrayList<>();
    private List<InstallationMaterial> electricalMaterials = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        rowDto = new RowDto(1L, 10L);
        rows.add(rowDto);
        constructionMaterial = InstallationMaterial.builder()
                .quantity(10L)
                .constructionMaterial(ConstructionMaterial.builder().name("construction material").materialId(1L).build())
                .build();
        constructionMaterials.add(constructionMaterial);
        electricalMaterial = InstallationMaterial.builder()
                .quantity(1L)
                .electricalMaterial(ElectricalMaterial.builder().name("electrical material").materialId(2L).build())
                .build();
        electricalMaterials.add(electricalMaterial);
        installationDto = InstallationDto.builder()
                .projectId(1L)
                .installationId(1L)
                .address("address")
                .constructionType(ConstructionType.TRAPEZE)
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .rows(rows)
                .lightingProtection(true)
                .acCableLength(10)
                .dcCableLength(20)
                .lgyCableLength(30)
                .build();
        installation =  Installation.builder()
                .installationId(1L)
                .address("address")
                .installationType(ConstructionType.TRAPEZE)
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .lightingProtection(true)
                .acCableLength(10)
                .dcCableLength(20)
                .lgyCableLength(30)
                .rows(new ArrayList<>())
                .materials(new ArrayList<>())
                .project(Project.builder().projectId(1L).build())
                .build();
        installationUpdateDto = InstallationUpdateDto.builder()
                .projectId(1L)
                .installationId(1L)
                .address("address")
                .constructionType(ConstructionType.TRAPEZE)
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .rows(rows)
                .lightingProtection(true)
                .acCableLength(10)
                .dcCableLength(20)
                .lgyCableLength(30)
                .build();
        project = Project.builder()
                .projectId(1L)
                .build();
        projectDto = ProjectDto.builder()
                .projectId(1L)
                .title("title")
                .investor("investor")
                .country("country")
                .voivodeship("voivodeship")
                .city("city")
                .modulePower(500)
                .moduleFrame(30)
                .moduleLength(2000)
                .moduleWidth(1000)
                .createdAt(LocalDateTime.of(2000, 1, 1, 1,0,0).toString())
                .installations(1)
                .build();
        user = UserEntity.builder()
                .username("user")
                .build();
    }
    @Test
    @DisplayName("Should create installation correctly")
    public void shouldCreateInstallationCorrectly() throws IOException {
        when(userEntityService.loadCurrentUser()).thenReturn(user);
        when(factory.createConstructionAssembler(any(Installation.class), any(ProjectDto.class))).thenReturn(constructionMaterialAssembler);
        when(constructionMaterialAssembler.createInstallationConstructionMaterials()).thenReturn(constructionMaterials);
        when(factory.createElectricalAssembler(any(Installation.class), anyLong(), any(ProjectDto.class))).thenReturn(electricalMaterialAssembler);
        when(electricalMaterialAssembler.createInstallationElectricalMaterials()).thenReturn(electricalMaterials);
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(installationRepository.save(any(Installation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Installation installation = installationService.createInstallation(installationDto, projectDto);

        assertEquals(1, installation.getRows().size());
        assertEquals("address", installation.getAddress());
        assertEquals(2, installation.getMaterials().size());
        assertEquals("construction material", installation.getMaterials().getFirst().getConstructionMaterial().getName());
        assertEquals("electrical material", installation.getMaterials().get(1).getElectricalMaterial().getName());
        assertEquals(1, installation.getProject().getProjectId());
    }
    @Test
    @DisplayName("Should throw InvalidRowQuantityException when create Installation and not enough modules")
    public void shouldThrowInvalidRowQuantityExceptionWhenCreateInstallationAndNotEnoughModules(){
        installationDto.setRows(List.of(new RowDto(1L, 1L)));

        InvalidRowQuantityException ex = assertThrows(InvalidRowQuantityException.class, () -> installationService.createInstallation(installationDto, projectDto));
        assertEquals("Each row should have at least 5 modules", ex.getUserMessage());
    }
    @Test
    @DisplayName("Should throw PowerOutOfBoundsException when create installation and power is too high")
    public void shouldThrowPowerOutOfBoundsExceptionWhenCreateInstallationAndPowerIsTooHigh(){
       projectDto.setModulePower(800);
       installationDto.setRows(List.of(
                new RowDto(1L, 22L),
                new RowDto(2L, 22L),
                new RowDto(3L, 22L)
        ));
        when(userEntityService.loadCurrentUser()).thenReturn(user);

        PowerOutOfBoundsException ex = assertThrows(PowerOutOfBoundsException.class, () -> installationService.createInstallation(installationDto, projectDto));
        assertEquals("Total power cannot exceed 49.995 kW and is: 52.8", ex.getUserMessage());
    }
    @Test
    @DisplayName("Should throw ResourceNotFoundException when create Installation and Project not found")
    public void shouldThrowResourceNotFoundExceptionWhenCreateInstallationAndProjectNotFound(){
        when(userEntityService.loadCurrentUser()).thenReturn(user);
        when(factory.createConstructionAssembler(any(Installation.class), any(ProjectDto.class))).thenReturn(constructionMaterialAssembler);
        when(constructionMaterialAssembler.createInstallationConstructionMaterials()).thenReturn(constructionMaterials);
        when(factory.createElectricalAssembler(any(Installation.class), anyLong(), any(ProjectDto.class))).thenReturn(electricalMaterialAssembler);
        when(electricalMaterialAssembler.createInstallationElectricalMaterials()).thenReturn(electricalMaterials);
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());


        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> installationService.createInstallation(installationDto, projectDto));
        assertEquals("Resource not found: Project", ex.getUserMessage());
    }
    @Test
    @DisplayName("Should remove Installation correctly")
    public void shouldRemoveInstallationCorrectly(){
        when(installationRepository.findById(anyLong())).thenReturn(Optional.of(installation));
        doNothing().when(installationRepository).delete(any(Installation.class));

        installationService.removeInstallation(1L);

        verify(installationRepository, times(1)).findById(eq(1L));
        verify(installationRepository, times(1)).delete(any(Installation.class));
    }
    @Test
    @DisplayName("Should throw InvalidIdException when remove Installation and Id is negative")
    public void shouldThrowInvalidIdExceptionWhenRemoveInstallationAndIdIsNegative(){
        InvalidIdException ex = assertThrows(InvalidIdException.class, () -> installationService.removeInstallation(-1L));
        assertEquals("Invalid ID", ex.getUserMessage());
    }
    @Test
    @DisplayName("Should update Installation correctly")
    public void shouldUpdateInstallationCorrectly(){
        when(userEntityService.loadCurrentUser()).thenReturn(user);
        when(installationRepository.findByIdWithRowsAndProject(anyLong())).thenReturn(Optional.of(installation));
        when(factory.createConstructionAssembler(any(Installation.class), any(ProjectDto.class))).thenReturn(constructionMaterialAssembler);
        when(constructionMaterialAssembler.createInstallationConstructionMaterials()).thenReturn(constructionMaterials);
        when(factory.createElectricalAssembler(any(Installation.class), anyLong(), any(ProjectDto.class))).thenReturn(electricalMaterialAssembler);
        when(electricalMaterialAssembler.createInstallationElectricalMaterials()).thenReturn(electricalMaterials);
        when(installationRepository.save(any(Installation.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Installation updatedInstallation = installationService.updateInstallation(1L, installationUpdateDto, projectDto);

        assertEquals(2, updatedInstallation.getMaterials().size());
        verify(constructionMaterialAssembler, times(1)).createInstallationConstructionMaterials();
        verify(electricalMaterialAssembler, times(1)).createInstallationElectricalMaterials();
    }
    @Test
    @DisplayName("Should throw resource not found exception when update Installation and Installation not found")
    public void shouldThrowResourceNotFoundExceptionWhenUpdateInstallationAndInstallationNotFound(){
        when(userEntityService.loadCurrentUser()).thenReturn(user);
        when(installationRepository.findByIdWithRowsAndProject(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> installationService.updateInstallation(1L, installationUpdateDto,  projectDto));
        assertEquals("Resource not found: Installation", ex.getUserMessage());
    }
    @Test
    @DisplayName("Should find all Installations for Project correctly")
    public void shouldThrowAllInstallationsForProjectCorrectly(){
        when(userEntityService.loadCurrentUser()).thenReturn(user);
        when(installationRepository.findAllInstallationsByProjectId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(installation)));

        Page<InstallationDto> installations = installationService.findAllInstallationsForProject(1L, new InstallationsRequestDto("ASC", 0, 10));

        assertEquals(1, installations.getTotalElements());
        assertEquals(1, installations.getTotalPages());
        assertEquals("address", installations.getContent().getFirst().getAddress());
    }
    @Test
    @DisplayName("Should return empty page when find all Installations for Project and no Installations found")
    public void shouldReturnEmptyPageWhenFindAllInstallationsForProjectAndNoInstallationsFound(){
        when(userEntityService.loadCurrentUser()).thenReturn(user);
        when(installationRepository.findAllInstallationsByProjectId(anyLong(), any(Pageable.class)))
                .thenReturn(Page.empty());

        Page<InstallationDto> installations = installationService.findAllInstallationsForProject(1L, new InstallationsRequestDto("ASC", 0, 10));

        assertEquals(0, installations.getTotalElements());
    }
    @Test
    @DisplayName("Should find Installation  by id correctly")
    public void shouldFindInstallationByIdCorrectly(){
        when(installationRepository.findByIdWithRowsAndProject(anyLong())).thenReturn(Optional.of(installation));

        InstallationDto returnedInstallation = installationService.findInstallationById(1L);

        assertEquals("address", returnedInstallation.getAddress());
    }
    @Test
    @DisplayName("Should find Installation update by id correctly")
    public void shouldFindInstallationUpdateByIdCorrectly(){
        when(installationRepository.findByIdWithRowsAndProject(anyLong())).thenReturn(Optional.of(installation));

        InstallationUpdateDto returnedInstallation = installationService.findInstallationUpdateById(1L);

        assertEquals("address", returnedInstallation.getAddress());
    }
    @Test
    @DisplayName("Should export installation materials to excel correctly")
    public void shouldCreateExcelFileCorrectlyWhenExportInstallationMaterialsToExcel() throws IOException {
        when(installationRepository.findByIdWithRowsAndProject(anyLong())).thenReturn(Optional.of(installation));
        when(installationMaterialRepository.fetchConstructionMaterialsForInstallation(anyLong())).thenReturn(Collections.emptyList());
        when(installationMaterialRepository.fetchElectricalMaterialsForInstallation(anyLong())).thenReturn(Collections.emptyList());
        when(materialsExporter.exportMaterialsToExcelForInstallation(any(Installation.class), anyMap(), anyMap())).thenReturn(new ByteArrayOutputStream());

        installationService.exportInstallationMaterialsToExcel(1L);

        verify(materialsExporter, times(1)).exportMaterialsToExcelForInstallation(installation, Collections.emptyMap(), Collections.emptyMap());
    }
}
