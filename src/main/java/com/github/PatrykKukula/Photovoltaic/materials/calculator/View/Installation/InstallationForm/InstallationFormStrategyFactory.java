package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Installation.InstallationForm;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationInterface;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation.InstallationUpdateDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Project.ProjectForm.ProjectFormStrategy;

public class InstallationFormStrategyFactory {
    @SuppressWarnings("unchecked")
    public <T extends InstallationInterface> InstallationFormStrategy<T> installationFormStrategy(T installationInterface){
        if (installationInterface instanceof InstallationDto) return (InstallationFormStrategy<T>) new AddInstallationFormStrategy();
        else if (installationInterface instanceof InstallationUpdateDto) return (InstallationFormStrategy<T>) new UpdateInstallationFormStrategy();
        else throw new RuntimeException("Exception during creating InstallationFormStrategy");
    }
}
