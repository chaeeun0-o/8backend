package com.eightjo.carrotclone.map;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "seoul_address")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String region1depthName;

    @Column(nullable = false)
    private String region2depthName;

    @Column(nullable = false)
    private String region3depthName;

    private double x;
    private double y;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<NearAddress> nearParentAddress = new ArrayList<>();

    @OneToMany(mappedBy = "child", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<NearAddress> nearChildAddress = new ArrayList<>();


    public Address(String region1depthName, String region2depthName, String region3depthName) {
        this.region1depthName = region1depthName;
        this.region2depthName = region2depthName;
        this.region3depthName = region3depthName;
        this.x = 0;
        this.y = 0;
    }

    public Address(String region1depthName, String region2depthName, String region3depthName, double x, double y) {
        this.region1depthName = region1depthName;
        this.region2depthName = region2depthName;
        this.region3depthName = region3depthName;
        this.x = x;
        this.y = y;
    }
}
