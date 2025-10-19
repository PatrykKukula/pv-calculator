package com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallationMaterialRepository extends JpaRepository<InstallationMaterial, Long> {
    @Query("SELECT m FROM InstallationMaterial m JOIN FETCH m.constructionMaterial WHERE m.installation.installationId= :installationId")
    List<InstallationMaterial> fetchConstructionMaterialsForInstallation(@Param(value = "installationId") Long installationId);
    @Query("SELECT m FROM InstallationMaterial m JOIN FETCH m.electricalMaterial WHERE m.installation.installationId= :installationId")
    List<InstallationMaterial> fetchElectricalMaterialsForInstallation(@Param(value = "installationId") Long installationId);
}
