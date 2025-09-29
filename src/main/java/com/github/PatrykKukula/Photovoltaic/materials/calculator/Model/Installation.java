package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.InstallationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private InstallationType installationType;
    @Column(nullable = false)
    private int modulePower;
    @Column(nullable = false)
    private int moduleLength;
    @Column(nullable = false)
    private int moduleHeight;
    @Column(nullable = false)
    private int moduleFrame;
    @Column(nullable = false)
    private int rows;
    @Column(nullable = false)
    private int moduleQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    @ManyToMany
    @JoinTable(
            name = "installation_materials",
            joinColumns = @JoinColumn(name = "installation_id", referencedColumnName = "installationId"),
            inverseJoinColumns = @JoinColumn(name = "material_quantity_id", referencedColumnName = "quantityId")
    )
    private List<MaterialQuantity> materials;
}
