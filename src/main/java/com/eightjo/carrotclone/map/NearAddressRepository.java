package com.eightjo.carrotclone.map;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NearAddressRepository extends JpaRepository<NearAddress,Long> {
    void deleteAllByParentId(Long parent_id);

    Optional<NearAddress> findByParentIdAndChildId(Long parent_id, Long child_id);

    List<NearAddress> findAllByParentId(Long parent_id);
}
