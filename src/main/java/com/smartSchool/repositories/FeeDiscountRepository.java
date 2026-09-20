package com.smartSchool.repositories;

import com.smartSchool.entities.FeeDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeDiscountRepository extends JpaRepository<FeeDiscount, Long> {

    boolean existsByDiscountCode(String discountCode);

    Optional<FeeDiscount> findByDiscountCode(String discountCode);
}