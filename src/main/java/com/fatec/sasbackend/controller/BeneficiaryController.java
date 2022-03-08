package com.fatec.sasbackend.controller;

import com.fatec.sasbackend.dto.BeneficiaryDTO;
import com.fatec.sasbackend.dto.BeneficiarySelectOptionsDTO;
import com.fatec.sasbackend.dto.UserDTO;
import com.fatec.sasbackend.dto.UserSelectOptionsDTO;
import com.fatec.sasbackend.model.user.UserRegisterModel;
import com.fatec.sasbackend.model.user.UserUpdateModel;
import com.fatec.sasbackend.service.BeneficiaryService;
import com.fatec.sasbackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/beneficiary")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    public BeneficiaryController(BeneficiaryService beneficiaryService) {
        this.beneficiaryService = beneficiaryService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<BeneficiaryDTO>> findAllPagedBeneficiary(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<BeneficiaryDTO> beneficiaryDTOs = beneficiaryService.findAllBeneficiary(pageable);

        return ResponseEntity.ok().body(beneficiaryDTOs);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> registerBeneficiary(@Valid @RequestBody BeneficiaryDTO beneficiaryDTO) {

        Boolean status = beneficiaryService.registerBeneficiary(beneficiaryDTO);

        if (Boolean.FALSE.equals(status)) {
            return ResponseEntity.badRequest().body("Error: Beneficiary already registered!");
        }
        return ResponseEntity.ok().body("Beneficiary Registered Succesfully!");
    }

    @GetMapping("/{beneficiaryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BeneficiaryDTO> findBeneficiaryById(
            @PathVariable Long beneficiaryId) {

        BeneficiaryDTO beneficiaryDTO = beneficiaryService.findBeneficiaryById(beneficiaryId);
        return new ResponseEntity<>(beneficiaryDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> updateBeneficiary(@Valid @RequestBody BeneficiaryDTO beneficiaryDTO) {

        Boolean status = beneficiaryService.updateBeneficiary(beneficiaryDTO);

        if (Boolean.FALSE.equals(status)) {
            return ResponseEntity.badRequest().body("Error: Update failed");
        }
        return ResponseEntity.ok().body("User Updated Succesfully!");
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<BeneficiaryDTO>> findPagedBeneficiaryByFilter(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "beneficiaryId") String beneficiaryId,
            @RequestParam(name = "cras") String cras,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<BeneficiaryDTO> filteredBeneficiary = beneficiaryService.findPagedBeneficiaryByFilter(name, beneficiaryId, cras, pageable);
        return new ResponseEntity<>(filteredBeneficiary, HttpStatus.OK);
    }

    @GetMapping("/selectOptions")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BeneficiarySelectOptionsDTO> findSelectOptions() {
        BeneficiarySelectOptionsDTO selectOptionsDTO = beneficiaryService.findSelectOptions();
        return new ResponseEntity<>(selectOptionsDTO, HttpStatus.OK);
    }

}
