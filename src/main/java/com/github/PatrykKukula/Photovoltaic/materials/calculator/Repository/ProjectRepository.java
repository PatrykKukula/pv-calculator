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
    @Query("SELECT p FROM Project p JOIN FETCH p.user u WHERE u.username= :username")
    Page<Project> findAllProjectsByUsername(@Param(value = "username") String username, Pageable pageable);
    @Query("SELECT p FROM Project p JOIN FETCH p.user LEFT JOIN FETCH p.installations u WHERE p.projectId= :projectId")
    Optional<Project> findByProjectIdWithUserAndInstallations(@Param(value = "projectId") Long projectId);
}
