package com.smartSchool.repositories;

import com.smartSchool.entities.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeTypeRepository extends JpaRepository<FeeType, Long> {

    boolean existsByCode(String code);

    Optional<FeeType> findByCode(String code);

    List<FeeType> findByFeeGroup(String feeGroup);
}