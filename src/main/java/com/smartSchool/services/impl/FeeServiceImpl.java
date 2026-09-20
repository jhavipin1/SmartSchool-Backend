package com.smartSchool.services.impl;

import com.smartSchool.dtos.fee.FeeRequestDto;
import com.smartSchool.dtos.fee.FeeResponseDto;
import com.smartSchool.entities.*;
import com.smartSchool.enums.FeePaymentStatus;
import com.smartSchool.repositories.*;
import com.smartSchool.services.FeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;
    private final FeeGroupRepository feeGroupRepository;
    private final FeeTypeRepository feeTypeRepository;
    private final FeeDiscountRepository feeDiscountRepository;

    @Override
    public FeeResponseDto createFee(FeeRequestDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + dto.getStudentId()));

        FeeType feeType = feeTypeRepository.findById(dto.getFeeTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Fee Type not found with ID: " + dto.getFeeTypeId()));

        FeeGroup feeGroup = dto.getFeeGroupId() != null
                ? feeGroupRepository.findById(dto.getFeeGroupId()).orElse(null)
                : null;

        FeeDiscount feeDiscount = dto.getFeeDiscountId() != null
                ? feeDiscountRepository.findById(dto.getFeeDiscountId()).orElse(null)
                : null;

        Fee fee = Fee.builder()
                .student(student)
                .feeGroup(feeGroup)
                .feeType(feeType)
                .feeDiscount(feeDiscount)
                .dueDate(dto.getDueDate())
                .amount(dto.getAmount())
                .paidAmount(dto.getPaidAmount() != null ? dto.getPaidAmount() : BigDecimal.ZERO)
                .fineAmount(dto.getFineAmount() != null ? dto.getFineAmount() : BigDecimal.ZERO)
                .discountAmount(dto.getDiscountAmount() != null ? dto.getDiscountAmount() : BigDecimal.ZERO)
                .status(dto.getStatus() != null ? dto.getStatus() : FeePaymentStatus.UNPAID)
                .description(dto.getDescription())
                .build();

        Fee savedFee = feeRepository.save(fee);
        return mapToResponseDto(savedFee);
    }

    @Override
    public FeeResponseDto updateFee(Long id, FeeRequestDto dto) {
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fee record not found with ID: " + id));

        if (!fee.getStudent().getId().equals(dto.getStudentId())) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + dto.getStudentId()));
            fee.setStudent(student);
        }

        if (!fee.getFeeType().getId().equals(dto.getFeeTypeId())) {
            FeeType feeType = feeTypeRepository.findById(dto.getFeeTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("Fee Type not found with ID: " + dto.getFeeTypeId()));
            fee.setFeeType(feeType);
        }

        fee.setFeeGroup(dto.getFeeGroupId() != null
                ? feeGroupRepository.findById(dto.getFeeGroupId()).orElse(null)
                : null);

        fee.setFeeDiscount(dto.getFeeDiscountId() != null
                ? feeDiscountRepository.findById(dto.getFeeDiscountId()).orElse(null)
                : null);

        fee.setDueDate(dto.getDueDate());
        fee.setAmount(dto.getAmount());
        if (dto.getPaidAmount() != null) fee.setPaidAmount(dto.getPaidAmount());
        if (dto.getFineAmount() != null) fee.setFineAmount(dto.getFineAmount());
        if (dto.getDiscountAmount() != null) fee.setDiscountAmount(dto.getDiscountAmount());
        if (dto.getStatus() != null) fee.setStatus(dto.getStatus());
        fee.setDescription(dto.getDescription());

        Fee updatedFee = feeRepository.save(fee);
        return mapToResponseDto(updatedFee);
    }

    @Override
    @Transactional(readOnly = true)
    public FeeResponseDto getFeeById(Long id) {
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fee record not found with ID: " + id));
        return mapToResponseDto(fee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeResponseDto> getAllFees() {
        return feeRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeResponseDto> getFeesByStudent(Long studentId) {
        return feeRepository.findByStudentId(studentId).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeResponseDto> getFeesByStatus(FeePaymentStatus status) {
        return feeRepository.findByStatus(status).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeResponseDto> getFeesByClass(Long classId) {
        return feeRepository.findByClassId(classId).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public FeeResponseDto recordPayment(Long feeId, BigDecimal paymentAmount, BigDecimal fineAmount) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new EntityNotFoundException("Fee record not found with ID: " + feeId));

        if (fineAmount != null) {
            fee.setFineAmount(fee.getFineAmount().add(fineAmount));
        }

        BigDecimal updatedPaidAmount = fee.getPaidAmount().add(paymentAmount);
        fee.setPaidAmount(updatedPaidAmount);

        BigDecimal netPayable = fee.getAmount()
                .add(fee.getFineAmount())
                .subtract(fee.getDiscountAmount())
                .subtract(fee.getPaidAmount());

        if (updatedPaidAmount.compareTo(netPayable) >= 0) {
            fee.setStatus(FeePaymentStatus.PAID);
        } else if (updatedPaidAmount.compareTo(BigDecimal.ZERO) > 0) {
            fee.setStatus(FeePaymentStatus.PARTIAL);
        }

        Fee updatedFee = feeRepository.save(fee);
        return mapToResponseDto(updatedFee);
    }

    @Override
    public void deleteFee(Long id) {
        if (!feeRepository.existsById(id)) {
            throw new EntityNotFoundException("Fee record not found with ID: " + id);
        }
        feeRepository.deleteById(id);
    }

    // Helper method to convert Entity to Response DTO
    private FeeResponseDto mapToResponseDto(Fee fee) {
        BigDecimal netPayable = fee.getAmount()
                .add(fee.getFineAmount() != null ? fee.getFineAmount() : BigDecimal.ZERO)
                .subtract(fee.getDiscountAmount() != null ? fee.getDiscountAmount() : BigDecimal.ZERO)
                .subtract(fee.getPaidAmount()!= null ? fee.getPaidAmount() : BigDecimal.ZERO);

        String fullName = String.join(" ",
                fee.getStudent().getFirstName(),
                fee.getStudent().getMiddleName() != null ? fee.getStudent().getMiddleName() : "",
                fee.getStudent().getLastName()).replaceAll("\\s+", " ").trim();

        return FeeResponseDto.builder()
                .id(fee.getId())
                .studentId(fee.getStudent().getId())
                .studentName(fullName)
                .admissionNumber(fee.getStudent().getAdmissionNumber())
                .feeGroupId(fee.getFeeGroup() != null ? fee.getFeeGroup().getId() : null)
                .feeGroupName(fee.getFeeGroup() != null ? fee.getFeeGroup().getName() : null)
                .feeTypeId(fee.getFeeType().getId())
                .feeTypeName(fee.getFeeType().getName())
                .feeTypeCode(fee.getFeeType().getCode())
                .feeDiscountId(fee.getFeeDiscount() != null ? fee.getFeeDiscount().getId() : null)
                .discountName(fee.getFeeDiscount() != null ? fee.getFeeDiscount().getName() : null)
                .discountCode(fee.getFeeDiscount() != null ? fee.getFeeDiscount().getDiscountCode() : null)
                .dueDate(fee.getDueDate())
                .amount(fee.getAmount())
                .paidAmount(fee.getPaidAmount())
                .fineAmount(fee.getFineAmount())
                .discountAmount(fee.getDiscountAmount())
                .netPayableAmount(netPayable)
                .status(fee.getStatus())
                .description(fee.getDescription())
                .build();
    }
}