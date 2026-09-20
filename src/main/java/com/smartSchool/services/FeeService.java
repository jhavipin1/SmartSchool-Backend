package com.smartSchool.services;

import com.smartSchool.dtos.fee.FeeRequestDto;
import com.smartSchool.dtos.fee.FeeResponseDto;
import com.smartSchool.enums.FeePaymentStatus;

import java.math.BigDecimal;
import java.util.List;

public interface FeeService {

    FeeResponseDto createFee(FeeRequestDto requestDto);

    FeeResponseDto updateFee(Long id, FeeRequestDto requestDto);

    FeeResponseDto getFeeById(Long id);

    List<FeeResponseDto> getAllFees();

    List<FeeResponseDto> getFeesByStudent(Long studentId);

    List<FeeResponseDto> getFeesByStatus(FeePaymentStatus status);

    List<FeeResponseDto> getFeesByClass(Long classId);

    FeeResponseDto recordPayment(Long feeId, BigDecimal paymentAmount, BigDecimal fineAmount);

    void deleteFee(Long id);
}