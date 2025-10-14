package com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT DISTINCT p FROM Project p JOIN FETCH p.user u LEFT JOIN p.installations WHERE u.username= :username")
    Page<Project> findAllProjectsByUsername(@Param(value = "username") String username, Pageable pageable);
    @Query("SELECT DISTINCT p FROM Project p JOIN FETCH p.user u LEFT JOIN p.installations WHERE u.username= :username AND p.title= :title")
    Page<Project> findAllProjectsByUsernameAndTitle(@Param(value = "username") String username, @Param(value = "title") String title, Pageable pageable);
    @Query("SELECT DISTINCT p FROM Project p JOIN FETCH p.user LEFT JOIN FETCH p.installations u WHERE p.projectId= :projectId")
    Optional<Project> findByProjectIdWithUserAndInstallations(@Param(value = "projectId") Long projectId);
    @Query("SELECT COALESCE(COUNT(i), 0) FROM Project p LEFT JOIN p.installations i WHERE p.projectId= :projectId")
    int getInstallationNumber(@Param(value = "projectId") Long projectId);
    @Query("""
    SELECT COALESCE(SUM(r.moduleQuantity), 0)
    FROM Project p
    JOIN p.installations i
    JOIN i.rows r
    WHERE p.projectId = :projectId
    """)
    Long getAllModulesByProjectId(@Param(value = "projectId") Long projectId);
    @Query("SELECT p.modulePower FROM Project p WHERE p.projectId= :projectId")
    Long getModulePowerByProjectId(@Param(value = "projectId") Long projectId);
}
