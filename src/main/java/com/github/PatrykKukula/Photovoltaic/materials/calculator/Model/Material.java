package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.MaterialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor  @AllArgsConstructor
@Data
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MaterialType type;
    @OneToMany(mappedBy = "material")
    private List<MaterialQuantity> quantities;
}
