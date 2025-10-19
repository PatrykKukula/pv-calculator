package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;

public interface InstallationFormStrategy<T extends InstallationInterface> {
    String getTitle();
    void save(InstallationService installationService, InstallationInterface installationInterface, ProjectDto projectDto, Long installationId);
    Class<T> getDtoClass();
}
