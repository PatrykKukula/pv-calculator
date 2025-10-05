package com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.ConstructionMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructionMaterialRepository extends JpaRepository<ConstructionMaterial, Long> {
    @Query("SELECT m FROM ConstructionMaterial m WHERE LOWER(m.name)= LOWER(:name)")
    Optional<ConstructionMaterial> fetchConstructionMaterialByName(@Param(value = "name") String name);
}
