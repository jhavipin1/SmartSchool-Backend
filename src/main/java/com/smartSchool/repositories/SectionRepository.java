// SectionRepository.java
package com.smartSchool.repositories;

import com.smartSchool.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByClassNameId(Long classNameId);
    Optional<Section> findBySectionNameAndClassName_Id(String sectionName, Long classId);
}