package com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallationMaterialRepository extends JpaRepository<InstallationMaterial, Long> {
    @Modifying
    @Query("DELETE FROM InstallationMaterial i WHERE i.installation.installationId= :installationId")
    void removeAllForInstallation(@Param(value = "installationId") Long installationId);
}
