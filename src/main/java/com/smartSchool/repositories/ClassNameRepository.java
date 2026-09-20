// ClassNameRepository.java
package com.smartSchool.repositories;

import com.smartSchool.entities.ClassName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClassNameRepository extends JpaRepository<ClassName, Long> {
    Optional<ClassName> findByClassName(String className);
}