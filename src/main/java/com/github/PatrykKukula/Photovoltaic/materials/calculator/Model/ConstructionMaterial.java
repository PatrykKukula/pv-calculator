package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor @Builder
@NoArgsConstructor
@Data
public class ConstructionMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @OneToMany(mappedBy = "constructionMaterial")
    private List<InstallationMaterial> quantities;
}
