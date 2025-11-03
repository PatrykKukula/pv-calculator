package com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.ElectricalMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectricalMaterialRepository extends JpaRepository<ElectricalMaterial, Long> {
    @Query("SELECT m FROM ElectricalMaterial m WHERE LOWER(m.name)= LOWER(:name)")
    Optional<ElectricalMaterial> fetchElectricalMaterialByName(@Param(value = "name") String name);
}
