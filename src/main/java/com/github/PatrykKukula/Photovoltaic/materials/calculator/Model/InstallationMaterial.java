package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder @AllArgsConstructor
@NoArgsConstructor
@Data
public class InstallationMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quantityId;
    @Column(nullable = false)
    private Long quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_material_id")
    private ConstructionMaterial constructionMaterial;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electrical_material_id")
    private ElectricalMaterial electricalMaterial;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installation_id")
    private Installation installation;
}
