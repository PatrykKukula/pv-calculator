package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Builder
public class ProjectDto implements ProjectInterface{
    private Long projectId;
    @NotEmpty(message = "Project title cannot be empty")
    @Size(max = 255, message = "Project title cannot exceed 255 characters")
    private String title;
    @Size(max = 255, message = "Investor cannot exceed 255 characters")
    private String investor;
    @Size(max = 255, message = "Country cannot exceed 64 characters")
    private String country;
    @Size(max = 255, message = "Voivodeship cannot exceed 64 characters")
    private String voivodeship;
    @Size(max = 255, message = "City cannot exceed 64 characters")
    private String city;
    @NotNull(message = "Module power must be between 350 and 800 W")
    @Max(value = 800, message = "Module power cannot exceed 800")
    @Min(value = 350, message = "Module power cannot be less than 350")
    private Integer modulePower;
    @NotNull(message = "Module length must be between 1600 and 2500 mm")
    @Min(value = 1600, message = "Module length cannot be less than 1600")
    @Max(value = 2500, message = "Module length cannot exceed 2500")
    private Integer moduleLength;
    @NotNull(message = "Module width must be between 990 and 1400 mm")
    @Min(value = 990, message = "Module width cannot be less than 990")
    @Max(value = 1400, message = "Module width cannot exceed 1400")
    private Integer moduleWidth;
    private Integer moduleFrame;
    private String createdAt;
    private int installations;

    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getInvestor() {
        return investor;
    }
    @Override
    public String getCountry() {
        return country;
    }
    @Override
    public String getVoivodeship() {
        return voivodeship;
    }
    @Override
    public String getCity() {
        return city;
    }
    @Override
    public Integer getModulePower() {
        return modulePower;
    }
    @Override
    public Integer getModuleLength() {
        return moduleLength;
    }
    @Override
    public Integer getModuleWidth() {
        return moduleWidth;
    }
    @Override
    public Integer getModuleFrame() {
        return moduleFrame;
    }
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public void setInvestor(String investor) {
        this.investor = investor;
    }
    @Override
    public void setCountry(String country) {
        this.country = country;
    }
    @Override
    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }
    @Override
    public void setCity(String city) {
        this.city = city;
    }
    @Override
    public void setModulePower(Integer modulePower) {
        this.modulePower = modulePower;
    }
    @Override
    public void setModuleLength(Integer moduleLength) {
        this.moduleLength = moduleLength;
    }
    @Override
    public void setModuleWidth(Integer moduleWidth) {
        this.moduleWidth = moduleWidth;
    }
    @Override
    public void setModuleFrame(Integer moduleFrame) {
        this.moduleFrame = moduleFrame;
    }
    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }
    public Long getProjectId(){
        return projectId;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getCreatedAt(){
        return createdAt;
    }
}
