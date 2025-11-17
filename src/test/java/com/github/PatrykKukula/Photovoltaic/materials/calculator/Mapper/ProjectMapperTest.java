package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectMapperTest {
    private ProjectDto projectDto;
    private Project project;
    private ProjectUpdateDto projectUpdateDto;

    @BeforeEach
    public void setUp() {
        projectDto = ProjectDto.builder()
                .title("title")
                .investor("investor")
                .country("country")
                .voivodeship("voivodeship")
                .city("city")
                .modulePower(500)
                .moduleFrame(30)
                .moduleLength(2000)
                .moduleWidth(1000)
                .build();
        project = Project.builder()
                .title("title")
                .investor("investor")
                .country("country")
                .city("city")
                .voivodeship("voivodeship")
                .modulePower(500)
                .moduleFrame(30)
                .moduleLength(2000)
                .moduleWidth(1000)
                .createdAt(LocalDateTime.now())
                .build();
        projectUpdateDto = ProjectUpdateDto.builder()
                .title("updated title")
                .investor("updated investor")
                .country("updated country")
                .voivodeship("updated voivodeship")
                .city("updated city")
                .modulePower(600)
                .moduleFrame(35)
                .moduleLength(2100)
                .moduleWidth(1100)
                .build();
    }
        @Test
        @DisplayName(value = "Should map ProjectDto to Project correctly")
        public void shouldMapProjectDtoToProjectCorrectly(){
            Project mappedProject = ProjectMapper.mapProjectDtoToProject(projectDto);

            assertEquals("title", mappedProject.getTitle());
            assertEquals("investor", mappedProject.getInvestor());
            assertEquals("country", mappedProject.getCountry());
            assertEquals("city", mappedProject.getCity());
            assertEquals("voivodeship", mappedProject.getVoivodeship());
            assertEquals(500, mappedProject.getModulePower());
            assertEquals(30, mappedProject.getModuleFrame());
            assertEquals(2000, mappedProject.getModuleLength());
            assertEquals(1000, mappedProject.getModuleWidth());
        }
    @Test
    @DisplayName(value = "Should map ProjectDto to Project correctly with null fields")
    public void shouldMapProjectDtoToProjectCorrectlyWithNullFields(){
        projectDto.setInvestor(null);
        projectDto.setCountry(null);
        projectDto.setVoivodeship(null);
        projectDto.setCity(null);

        Project mappedProject = ProjectMapper.mapProjectDtoToProject(projectDto);

        assertEquals("", mappedProject.getInvestor());
        assertEquals("", mappedProject.getCountry());
        assertEquals("", mappedProject.getCity());
        assertEquals("", mappedProject.getVoivodeship());
    }
        @Test
        @DisplayName("Should map Project to ProjectDto correctly")
        public void shouldMapProjectToProjectDtoCorrectly(){
            ProjectDto mappedProject = ProjectMapper.mapProjectToProjectDto(project);

            assertEquals("title", mappedProject.getTitle());
            assertEquals("investor", mappedProject.getInvestor());
            assertEquals("country", mappedProject.getCountry());
            assertEquals("city", mappedProject.getCity());
            assertEquals("voivodeship", mappedProject.getVoivodeship());
            assertEquals(500, mappedProject.getModulePower());
            assertEquals(30, mappedProject.getModuleFrame());
            assertEquals(2000, mappedProject.getModuleLength());
            assertEquals(1000, mappedProject.getModuleWidth());
        }
    @Test
    @DisplayName(value = "Should map Project to ProjectDto correctly with null fields")
    public void shouldMapProjectToProjectDtoCorrectlyWithNullFields(){
        project = Project.builder()
                .investor(null)
                .country(null)
                .voivodeship(null)
                .city(null)
                .createdAt(LocalDateTime.now())
                .build();
        ProjectDto mappedProject = ProjectMapper.mapProjectToProjectDto(project);

        assertEquals("", mappedProject.getInvestor());
        assertEquals("", mappedProject.getCountry());
        assertEquals("", mappedProject.getCity());
        assertEquals("", mappedProject.getVoivodeship());
    }
    @Test
    @DisplayName("Should map ProjectUpdateDto to Project correctly")
    public void shouldMapProjectUpdateDtoToProjectCorrectly(){
        Project mappedProject = ProjectMapper.mapProjectUpdateDtoToProject(projectUpdateDto, new Project());

        assertEquals("updated title", mappedProject.getTitle());
        assertEquals("updated investor", mappedProject.getInvestor());
        assertEquals("updated country", mappedProject.getCountry());
        assertEquals("updated city", mappedProject.getCity());
        assertEquals("updated voivodeship", mappedProject.getVoivodeship());
        assertEquals(600, mappedProject.getModulePower());
        assertEquals(35, mappedProject.getModuleFrame());
        assertEquals(2100, mappedProject.getModuleLength());
        assertEquals(1100, mappedProject.getModuleWidth());
    }
}
