package com.smartSchool.repositories;

import com.smartSchool.entities.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RackRepository extends JpaRepository<Rack, Long>, JpaSpecificationExecutor<Rack> {

    List<Rack> findByRackCodeContainingIgnoreCase(String rackCode);

    List<Rack> findByLocationContainingIgnoreCase(String location);
}
