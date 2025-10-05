package com.github.PatrykKukula.Photovoltaic.materials.calculator.Model;

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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    @Column(nullable = false)
    private String title;
    private String investor;
    @Column(length = 64)
    private String country;
    @Column(length = 64)
    private String voivodeship;
    @Column(length = 64)
    private String city;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private int modulePower;
    @Column(nullable = false)
    private int moduleLength;
    @Column(nullable = false)
    private int moduleWidth;
    @Column(nullable = false)
    private int moduleFrame;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Installation> installations;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @PrePersist
    private void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
}
