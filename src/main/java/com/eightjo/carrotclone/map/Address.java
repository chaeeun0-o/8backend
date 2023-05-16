package com.eightjo.carrotclone.map;

import jakarta.persistence.*;
import lombok.*;

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


    public Address(String region1depthName, String region2depthName, String region3depthName) {
        this.region1depthName = region1depthName;
        this.region2depthName = region2depthName;
        this.region3depthName = region3depthName;
        this.x = 0;
        this.y = 0;
    }
}
