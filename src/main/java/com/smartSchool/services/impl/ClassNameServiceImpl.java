package com.smartSchool.services.impl;

import com.smartSchool.dtos.className.ClassNameRequestDto;
import com.smartSchool.dtos.className.ClassNameResponseDto;
import com.smartSchool.dtos.className.ClassWithSectionsRequestDto;
import com.smartSchool.dtos.section.SectionResponseDto;
import com.smartSchool.entities.ClassName;
import com.smartSchool.entities.Section;
import com.smartSchool.repositories.ClassNameRepository;
import com.smartSchool.services.ClassNameService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassNameServiceImpl implements ClassNameService {

    private final ClassNameRepository classNameRepository;

    public ClassNameServiceImpl(ClassNameRepository classNameRepository) {
        this.classNameRepository = classNameRepository;
    }

    @Override
    public ClassNameResponseDto createClass(ClassNameRequestDto dto) {
        ClassName className = ClassName.builder().className(dto.getClassName()).build();
        ClassName saved = classNameRepository.save(className);
        return new ClassNameResponseDto(saved.getId(), saved.getClassName(), new ArrayList<>());
    }

    @Override
    public List<ClassNameResponseDto> createClassesBulk(List<ClassNameRequestDto> dtos) {
        List<ClassName> classes = dtos.stream()
                .map(dto -> ClassName.builder().className(dto.getClassName()).build())
                .collect(Collectors.toList());

        List<ClassName> saved = classNameRepository.saveAll(classes);

        return saved.stream()
                .map(c -> new ClassNameResponseDto(
                        c.getId(),
                        c.getClassName(),
                        c.getSections().stream()
                                .map(s -> new SectionResponseDto(s.getId(), s.getSectionName(), c.getId()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassNameResponseDto> getAllClasses() {
        return classNameRepository.findAll().stream()
                .map(c -> new ClassNameResponseDto(c.getId(), c.getClassName(),
                        c.getSections().stream()
                                .map(s -> new SectionResponseDto(s.getId(), s.getSectionName(), c.getId()))
                                .toList()))
                .toList();
    }

    @Override
    public ClassNameResponseDto getClassById(Long id) {
        ClassName c = classNameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        return new ClassNameResponseDto(c.getId(), c.getClassName(),
                c.getSections().stream()
                        .map(s -> new SectionResponseDto(s.getId(), s.getSectionName(), c.getId()))
                        .toList());
    }

    @Override
    public ClassNameResponseDto updateClass(Long id, ClassNameRequestDto dto) {
        ClassName c = classNameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        c.setClassName(dto.getClassName());
        ClassName updated = classNameRepository.save(c);
        return new ClassNameResponseDto(updated.getId(), updated.getClassName(), new ArrayList<>());
    }

    @Override
    public void deleteClass(Long id) {
        classNameRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ClassNameResponseDto createClassWithSections(ClassWithSectionsRequestDto dto) {
        ClassName classEntity = ClassName.builder()
                .className(dto.getClassName())
                .build();

        if (dto.getSections() != null && !dto.getSections().isEmpty()) {
            List<Section> sectionEntities = dto.getSections().stream()
                    .map(secDto -> Section.builder()
                            .sectionName(secDto.getSectionName())
                            .className(classEntity)
                            .build())
                    .collect(Collectors.toList());

            classEntity.setSections(sectionEntities);
        }

        ClassName saved = classNameRepository.save(classEntity);

        return ClassNameResponseDto.builder()
                .id(saved.getId())
                .className(saved.getClassName())
                .sections(saved.getSections().stream()
                        .map(sec -> new SectionResponseDto(
                                sec.getId(),
                                sec.getSectionName(),
                                sec.getClassName().getId()
                        ))
                        .collect(Collectors.toList()))
                .build();

    }



}

