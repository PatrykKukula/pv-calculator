package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectRequestDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidIdException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidOwnershipException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Construction.ConstructionMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.MaterialBuilderFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.*;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserEntityService userService;
    @Mock
    private MaterialBuilderFactory builderFactory;
    @Mock
    private ConstructionMaterialAssembler constructionMaterialBuilder;
    @Mock
    private ElectricalMaterialAssembler electricalMaterialAssembler;
    @Mock
    private CacheManager cacheManager;
    @InjectMocks
    private ProjectService projectService;

    private ProjectDto projectDto;
    private UserEntity user;
    private Project project;
    private ProjectRequestDto projectRequestDto;
    private ProjectUpdateDto projectUpdateDto;

    @BeforeEach
    public void setUp(){
        projectDto = ProjectDto.builder()
                .title("project")
                .country("country")
                .city("city")
                .voivodeship("voivodeship")
                .investor("investor")
                .moduleFrame(30)
                .modulePower(500)
                .moduleLength(2000)
                .moduleWidth(1000)
                .build();
        user = UserEntity.builder()
                .userId(1L)
                .username("user")
                .roles(List.of(Role.builder().roleId(1L).name("USER").build()))
                .createdAt(LocalDateTime.now())
                .build();
        project = Project.builder()
                .title("project")
                .country("country")
                .city("city")
                .voivodeship("voivodeship")
                .investor("investor")
                .moduleFrame(30)
                .modulePower(500)
                .moduleLength(2000)
                .moduleWidth(1000)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        projectRequestDto = ProjectRequestDto.builder()
                .title("project")
                .sortDirection("ASC")
                .pageNo(0)
                .pageSize(10)
                .build();
        projectUpdateDto = ProjectUpdateDto.builder()
                .title("updated project")
                .country("updated country")
                .city("updated city")
                .voivodeship("updated voivodeship")
                .investor("updated investor")
                .modulePower(500)
                .moduleWidth(1000)
                .moduleLength(2000)
                .moduleFrame(30)
                .build();
    }
    @Test
    @DisplayName("Should create project correctly")
    public void shouldCreateProjectCorrectly(){
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        projectService.createProject(projectDto);
        verify(projectRepository).save(captor.capture());
        Project createdProject = captor.getValue();

        assertEquals("project", createdProject.getTitle());
        assertEquals("country", createdProject.getCountry());
        assertEquals("city", createdProject.getCity());
        assertEquals("voivodeship", createdProject.getVoivodeship());
        assertEquals("investor", createdProject.getInvestor());
        assertEquals(500, createdProject.getModulePower());
        assertEquals(1000, createdProject.getModuleWidth());
        assertEquals(2000, createdProject.getModuleLength());
        assertEquals(30, createdProject.getModuleFrame());
    }
    @Test
    @DisplayName("Should throw AuthenticationCredentialsNotFoundException when loading current user and user not found")
    public void shouldThrowAuthenticationCredentialsNotFoundExceptionWhenLoadingCurrentUserAndUserNotFound(){
        when(userService.loadCurrentUser()).thenThrow(new AuthenticationCredentialsNotFoundException("Log in to proceed"));

        AuthenticationCredentialsNotFoundException ex = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> projectService.createProject(projectDto));
        assertEquals("Log in to proceed", ex.getMessage());
        verifyNoInteractions(projectRepository);
    }
    @Test
    @DisplayName("Should find all projects correctly with no title param")
    public void shouldFindAllProjectsCorrectlyWithNoTitleParam(){
        projectRequestDto.setTitle(null);
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findAllProjectsByUsername(anyString(), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(project)));

        Page<ProjectDto> projects = projectService.findAllProjects(projectRequestDto);

        assertEquals(1, projects.getTotalElements());
        assertEquals(1, projects.getTotalPages());
        assertEquals("project", projects.getContent().getFirst().getTitle());
    }
    @Test
    @DisplayName("Should find all projects correctly with title param")
    public void shouldFindAllProjectsCorrectlyWithTitleParam(){
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findAllProjectsByUsernameAndTitle(anyString(), anyString(), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(project)));

        Page<ProjectDto> projects = projectService.findAllProjects(projectRequestDto);

        assertEquals(1, projects.getTotalElements());
        assertEquals(1, projects.getTotalPages());
        assertEquals("project", projects.getContent().getFirst().getTitle());
    }
    @Test
    @DisplayName("Should return empty page when find all projects and no projects found")
    public void shouldReturnEmptyPageWhenFindAllUsersAndNoProjectsFound(){
        projectRequestDto.setTitle(null);
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findAllProjectsByUsername(anyString(), any(Pageable.class))).thenReturn(Page.empty());

        Page<ProjectDto> projects = projectService.findAllProjects(projectRequestDto);

        assertEquals(0, projects.getTotalElements());
        assertEquals(0, projects.getContent().size());
    }
    @Test
    @DisplayName("Should throw AuthenticationCredentialsNotFoundException when invoke loadCurrentUser and user is not logged in")
    public void shouldThrowAuthenticationCredentialsNotFoundExceptionWhenFindAllProjectsAndUserIsNotLoggedIn(){
        when(userService.loadCurrentUser()).thenThrow(AuthenticationCredentialsNotFoundException.class);

        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> projectService.findAllProjects(projectRequestDto));
    }
    @Test
    @DisplayName("Should remove project correctly")
    public void shouldRemoveProjectCorrectly(){
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findByProjectIdWithUserAndInstallations(anyLong())).thenReturn(Optional.of(project));

        projectService.removeProject(1L);

        verify(projectRepository, times(1)).delete(any(Project.class));
    }
    @Test
    @DisplayName("Should throw InvalidIdException when id is null")
    public void shouldThrowInvalidIdExceptionWhenIdIsNull(){
        InvalidIdException ex = assertThrows(InvalidIdException.class, () -> projectService.removeProject(null));
        assertEquals("ID cannot be less than 1 but was: null", ex.getMessage());
        assertEquals("Invalid ID", ex.getUserMessage());
    }
    @Test
    @DisplayName("Should throw InvalidOwnershipException when attempt to take action on other user project")
    public void shouldThrowInvalidOwnershipExceptionWhenTryToTakeActionOnOtherUserProject(){
        project.setUser(UserEntity.builder().userId(2L).build());
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findByProjectIdWithUserAndInstallations(anyLong())).thenReturn(Optional.of(project));

        InvalidOwnershipException ex = assertThrows(InvalidOwnershipException.class, () -> projectService.removeProject(1L));
        assertTrue(ex.getMessage().contains("doesn't belong to current user"));
    }
    @Test
    @DisplayName("Should update project correctly when modules are not updated")
    public void shouldUpdateProjectCorrectlyWhenModulesAreNotUpdated(){
        projectUpdateDto.setModuleFrame(30);
        projectUpdateDto.setModuleLength(2000);
        projectUpdateDto.setModuleWidth(1000);
        project.setInstallations(List.of(setUpInstallation()));
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findByProjectIdWithUserAndInstallations(anyLong())).thenReturn(Optional.of(project));

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        projectService.updateProject(1L, projectUpdateDto);
        verify(projectRepository).save(captor.capture());
        Project updatedProject = captor.getValue();

        assertEquals("updated project", updatedProject.getTitle());
        assertEquals("updated country", updatedProject.getCountry());
        assertEquals("updated city", updatedProject.getCity());
        assertEquals("updated voivodeship", updatedProject.getVoivodeship());
        assertEquals("updated investor", updatedProject.getInvestor());
        verifyNoInteractions(builderFactory);
    }
    @Test
    @DisplayName("Should update materials when updateProject and module size is updated")
    public void shouldUpdateMaterialsWhenUpdateProjectAndModuleSizeIsUpdated(){
        project.setInstallations(List.of(setUpInstallation()));
        projectUpdateDto.setModuleFrame(35);
        projectUpdateDto.setModuleWidth(1100);
        projectUpdateDto.setModuleLength(2100);
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findByProjectIdWithUserAndInstallations(anyLong())).thenReturn(Optional.of(project));
        when(builderFactory.createConstructionAssembler(any(), any())).thenReturn(constructionMaterialBuilder);
        when(constructionMaterialBuilder.createInstallationConstructionMaterials())
                .thenReturn(List.of(InstallationMaterial.builder().constructionMaterial(ConstructionMaterial.builder().name("End clamp 35mm").build()).build()));

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        projectService.updateProject(1L, projectUpdateDto);
        verify(projectRepository).save(captor.capture());
        Project updatedProject = captor.getValue();

        assertEquals(35, updatedProject.getModuleFrame());
        assertEquals(1100, updatedProject.getModuleWidth());
        assertEquals(2100, updatedProject.getModuleLength());
        verifyNoInteractions(electricalMaterialAssembler);
    }
    @Test
    @DisplayName("Should update materials when updateProject and module power is updated")
    public void shouldUpdateMaterialsWhenUpdateProjectAndModulePowerIsUpdated(){
        project.setInstallations(List.of(setUpInstallation()));
        projectUpdateDto.setModulePower(600);
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findByProjectIdWithUserAndInstallations(anyLong())).thenReturn(Optional.of(project));
        when(builderFactory.createElectricalAssembler(any(), any())).thenReturn(electricalMaterialAssembler);
        when(electricalMaterialAssembler.createInstallationElectricalMaterials())
                .thenReturn(List.of(InstallationMaterial.builder().electricalMaterial(ElectricalMaterial.builder().name("electrical material").build()).build()));

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        projectService.updateProject(1L, projectUpdateDto);
        verify(projectRepository).save(captor.capture());
        Project updatedProject = captor.getValue();

        assertEquals(600, updatedProject.getModulePower());
        verifyNoInteractions(constructionMaterialBuilder);
    }
    @Test
    @DisplayName("Should get Installation count for Project correctly")
    public void shouldGetInstallationCountForProjectCorrectly(){
        when(projectRepository.getInstallationNumber(anyLong())).thenReturn(1);

        Integer count = projectService.getInstallationCountForProject(1L);

        assertEquals(1, count);
    }
    @Test
    @DisplayName("Should get total power for Project correctly")
    public void shouldGetTotalPowerForProjectCorrectly(){
        when(projectRepository.getModulePowerByProjectId(anyLong())).thenReturn(500L);
        when(projectRepository.getAllModulesByProjectId(1L)).thenReturn(21L);

        Double totalPower = projectService.getTotalPowerForProject(1L);

        assertEquals(10.5, totalPower);
    }
    private Installation setUpInstallation(){
        List<InstallationMaterial> materials = new ArrayList<>();
        materials.add(InstallationMaterial.builder().constructionMaterial(ConstructionMaterial.builder().name("End clamp 40mm").build()).build());
        return Installation.builder()
                .installationId(1L)
                .installationType(ConstructionType.TRAPEZE)
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .rows(List.of(Row.builder().rowId(1L).rowNumber(1L).moduleQuantity(10L).build()))
                .materials(materials)
                .build();
    }

}
