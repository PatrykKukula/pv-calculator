package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectRequestDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidIdException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidOwnershipException;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.MaterialBuilderFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction.ConstructionMaterialBuilder;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.*;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ProjectRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Security.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private InstallationMaterialRepository installationMaterialRepository;
    @Mock
    private UserEntityService userService;
    @Mock
    private MaterialBuilderFactory builderFactory;
    @Mock
    private ConstructionMaterialBuilder constructionMaterialBuilder;
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
    @DisplayName("Should find all projects correctly")
    public void shouldFindAllProjectsCorrectly(){
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findAllProjectsByUsername(anyString(), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(project)));

        Page<ProjectDto> projects = projectService.findAllProjects(projectRequestDto);

        assertEquals(1, projects.getTotalElements());
        assertEquals(1, projects.getTotalPages());
        assertEquals("project", projects.getContent().getFirst().getTitle());
    }
    @Test
    @DisplayName("Should return empty page when find all projects and no projects found")
    public void shouldReturnEmptyPageWhenFindAllUsersAndNoProjectsFound(){
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
        assertEquals("ID cannot be less than 1", ex.getMessage());
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
    @DisplayName("Should update project correctly")
    public void shouldUpdateProjectCorrectly(){
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
        assertEquals(500, updatedProject.getModulePower());
        assertEquals(1000, updatedProject.getModuleWidth());
        assertEquals(2000, updatedProject.getModuleLength());
        assertEquals(30, updatedProject.getModuleFrame());
    }
    @Test
    @DisplayName("Should update materials when updateProject and module frame is updated")
    public void shouldUpdateMaterialsWhenUpdateProjectAndModuleFrameIsUpdated(){
        projectUpdateDto.setModuleFrame(35);
        project.setInstallations(List.of(setUpInstallation()));
        when(userService.loadCurrentUser()).thenReturn(user);
        when(projectRepository.findByProjectIdWithUserAndInstallations(anyLong())).thenReturn(Optional.of(project));
        when(constructionMaterialBuilder.createInstallationConstructionMaterials()).thenReturn(Collections.emptyList());
        when(builderFactory.createConstructionBuilder(any(), any())).thenReturn(constructionMaterialBuilder);
        when(constructionMaterialBuilder.createInstallationConstructionMaterials()).thenReturn(List.of(new InstallationMaterial()));
        
        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        projectService.updateProject(1L, projectUpdateDto);
        verify(projectRepository).save(captor.capture());
        Project updatedProject = captor.getValue();

        verify(installationMaterialRepository, times(1)).removeAllForInstallation(anyLong());
        assertEquals(35, updatedProject.getModuleFrame());
    }
    private Installation setUpInstallation(){
        return Installation.builder()
                .installationId(1L)
                .installationType(ConstructionType.TRAPEZE)
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .rows(List.of(Row.builder().rowId(1L).rowNumber(1L).moduleQuantity(10L).build()))
                .build();
    }

}
