package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder @AllArgsConstructor
@NoArgsConstructor
@Data
public class MaterialQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quantityId;

    @Column(nullable = false)
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    @ManyToMany(mappedBy = "materials")
    private List<Installation> installations;
}
