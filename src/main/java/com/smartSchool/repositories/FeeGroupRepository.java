package com.smartSchool.repositories;

import com.smartSchool.entities.FeeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeGroupRepository extends JpaRepository<FeeGroup, Long> {

    boolean existsByName(String name);

    Optional<FeeGroup> findByName(String name);
}