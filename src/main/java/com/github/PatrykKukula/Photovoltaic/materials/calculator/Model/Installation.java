package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class Installation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long installationId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ConstructionType installationType;
    private ModuleOrientation moduleOrientation;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;
    @OneToMany(mappedBy = "installation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InstallationMaterial> materials;
    @OneToMany(mappedBy = "installation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Row> rows;

    @PrePersist
    private void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
}
