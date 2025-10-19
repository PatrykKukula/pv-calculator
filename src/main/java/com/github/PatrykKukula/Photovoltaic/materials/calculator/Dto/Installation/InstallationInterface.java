package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;

import java.util.List;

public interface InstallationInterface {
    Long getInstallationId();
    String getAddress();
    ConstructionType getConstructionType();
    ModuleOrientation getModuleOrientation();
    List<RowDto> getRows();
    boolean isLightingProtection();
    Integer getAcCableLength();
    Integer getDcCableLength();
    Integer getLgyCableLength();
    void setRows(List<RowDto> rows);
}
