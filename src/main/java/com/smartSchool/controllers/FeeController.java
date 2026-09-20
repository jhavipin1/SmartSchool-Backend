package com.smartSchool.controllers;

import com.smartSchool.dtos.fee.FeeRequestDto;
import com.smartSchool.dtos.fee.FeeResponseDto;
import com.smartSchool.enums.FeePaymentStatus;
import com.smartSchool.services.FeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
public class FeeController {

    private final FeeService feeService;

    // 1. Assign / Create new fee
    @PostMapping
    public ResponseEntity<FeeResponseDto> createFee(@Valid @RequestBody FeeRequestDto requestDto) {
        FeeResponseDto response = feeService.createFee(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Update existing fee assignment
    @PutMapping("/{id}")
    public ResponseEntity<FeeResponseDto> updateFee(
            @PathVariable Long id,
            @Valid @RequestBody FeeRequestDto requestDto) {
        FeeResponseDto response = feeService.updateFee(id, requestDto);
        return ResponseEntity.ok(response);
    }

    // 3. Get fee by ID
    @GetMapping("/{id}")
    public ResponseEntity<FeeResponseDto> getFeeById(@PathVariable Long id) {
        FeeResponseDto response = feeService.getFeeById(id);
        return ResponseEntity.ok(response);
    }

    // 4. Get all fees
    @GetMapping
    public ResponseEntity<List<FeeResponseDto>> getAllFees() {
        List<FeeResponseDto> fees = feeService.getAllFees();
        return ResponseEntity.ok(fees);
    }

    // 5. Get fees for a specific student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<FeeResponseDto>> getFeesByStudent(@PathVariable Long studentId) {
        List<FeeResponseDto> fees = feeService.getFeesByStudent(studentId);
        return ResponseEntity.ok(fees);
    }

    // 6. Get fees filtered by status (UNPAID, PAID, PARTIAL, OVERDUE)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FeeResponseDto>> getFeesByStatus(@PathVariable FeePaymentStatus status) {
        List<FeeResponseDto> fees = feeService.getFeesByStatus(status);
        return ResponseEntity.ok(fees);
    }

    // 7. Get fees by class ID
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<FeeResponseDto>> getFeesByClass(@PathVariable Long classId) {
        List<FeeResponseDto> fees = feeService.getFeesByClass(classId);
        return ResponseEntity.ok(fees);
    }

    // 8. Record payment against a fee
    @PatchMapping("/{id}/pay")
    public ResponseEntity<FeeResponseDto> recordPayment(
            @PathVariable Long id,
            @RequestParam BigDecimal paymentAmount,
            @RequestParam(required = false) BigDecimal fineAmount) {
        FeeResponseDto response = feeService.recordPayment(id, paymentAmount, fineAmount);
        return ResponseEntity.ok(response);
    }

    // 9. Delete fee record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
        feeService.deleteFee(id);
        return ResponseEntity.noContent().build();
    }
}