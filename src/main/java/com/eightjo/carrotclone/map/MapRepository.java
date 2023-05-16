package com.eightjo.carrotclone.map;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByRegion1depthNameAndRegion2depthNameAndRegion3depthName(String region1depthName, String region2depthName, String region3depthName);

    void findByAddress(Address address1);

    Optional<Address> findByXY(double x, double y);
}
