package com.fatec.sasbackend.beneficiary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/beneficiary")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;
    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryController(BeneficiaryService beneficiaryService, BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryService = beneficiaryService;
        this.beneficiaryRepository = beneficiaryRepository;
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
    public ResponseEntity<BeneficiaryDTO> registerBeneficiary(@Valid @RequestBody BeneficiaryDTO beneficiaryDTO) {

        BeneficiaryDTO dto = beneficiaryService.registerBeneficiary(beneficiaryDTO);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BeneficiaryDTO> findBeneficiaryById(
            @PathVariable Long id) {

        BeneficiaryDTO beneficiaryDTO = beneficiaryService.findById(id);
        return new ResponseEntity<>(beneficiaryDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BeneficiaryDTO> updateBeneficiary(@Valid @RequestBody BeneficiaryDTO beneficiaryDTO) {

        BeneficiaryDTO dto = beneficiaryService.updateBeneficiary(beneficiaryDTO);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<BeneficiaryDTO>> findPagedBeneficiaryByFilter(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "rg") String rg,
            @RequestParam(name = "cpf") String cpf,
            @RequestParam(name = "cras") String cras,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<BeneficiaryDTO> filteredBeneficiary = beneficiaryService.findPagedBeneficiaryByFilter(name, rg, cpf, cras, pageable);
        return new ResponseEntity<>(filteredBeneficiary, HttpStatus.OK);
    }

    @GetMapping("/select-options")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BeneficiarySelectOptionsDTO> findSelectOptions() {
        BeneficiarySelectOptionsDTO selectOptionsDTO = beneficiaryService.findSelectOptions();
        return new ResponseEntity<>(selectOptionsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteBeneficiary(@RequestParam String id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register-beneficiary-product")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BeneficiaryProductsDTO> registerBeneficiaryProducts(@RequestBody BeneficiaryProductsDTO beneficiaryProductsDTO) {
        BeneficiaryProductsDTO beneficiaryProductsDTOS = beneficiaryService.registerBeneficiaryProducts(beneficiaryProductsDTO);
        return ResponseEntity.ok().body(beneficiaryProductsDTOS);
    }

    @GetMapping("/beneficiary-products")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BeneficiaryProductsDTO> findBeneficiaryProducts(@RequestParam String id) {
        BeneficiaryProductsDTO beneficiaryProductsDTOS = beneficiaryService.findBeneficiaryProducts(Long.parseLong(id));
        return ResponseEntity.ok().body(beneficiaryProductsDTOS);
    }


    @PostMapping("/benefit-beneficiary")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Long> benefitBeneficiary(@RequestBody Long id) {

        Long beneficiaryId = beneficiaryService.benefitBeneficiary(id);

        return ResponseEntity.ok().body(beneficiaryId);
    }

}
