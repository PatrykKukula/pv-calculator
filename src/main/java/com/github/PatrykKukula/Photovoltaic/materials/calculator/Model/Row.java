package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@AllArgsConstructor
@Data
public class Row {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rowId;

    @Column(nullable = false)
    private Long rowNumber;
    @Column(nullable = false)
    private Long moduleQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installationId", nullable = false)
    private Installation installation;
}
