package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PhaseNumber;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Installation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long installationId;

    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ConstructionType installationType;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ModuleOrientation moduleOrientation;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PhaseNumber phaseNumber;
    @Column(nullable = false)
    private Integer strings;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;
    @OneToMany(mappedBy = "installation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InstallationMaterial> materials;
    @OneToMany(mappedBy = "installation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Row> rows;
    @Column(nullable = false)
    private boolean lightingProtection;
    @Column(nullable = false)
    private Integer acCableLength;
    @Column(nullable = false)
    private Integer dcCableLength;
    @Column(nullable = false)
    private Integer lgyCableLength;

    @PrePersist
    private void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
}
