package com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {
    @Query("SELECT DISTINCT i FROM Installation i JOIN i.project p JOIN FETCH i.rows WHERE p.projectId = :projectId")
    Page<Installation> findAllInstallationsByProjectId(@Param(value = "projectId") Long projectId, Pageable pageable);
    @Query("SELECT i FROM Installation i JOIN FETCH i.rows JOIN FETCH i.project WHERE i.installationId= :installationId")
    Optional<Installation> findByIdWithRowsAndProject(@Param(value = "installationId") Long installationId);
}
