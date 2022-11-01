package com.fatec.sasbackend.covered;

import com.fatec.sasbackend.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/covered")
@RequiredArgsConstructor
public class CoveredController {

    private final CoveredService coveredService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<Covered>> findAllPagedCovered(
            @PageableDefault(page = 0, size = 10, sort = "beneficiaryName", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<Covered> covered = coveredService.findAllCovered(pageable);
        return ResponseEntity.ok().body(covered);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<Covered>> findPagedCoveredByFilter(
            @RequestParam(name = "beneficiaryName") String beneficiaryName,
            @RequestParam(name = "beneficiaryCpf") String beneficiaryCpf,
            @RequestParam(name = "crasName") String crasName,
            @RequestParam(name = "benefitDeliveryDate") String benefitDeliveryDate,
            @PageableDefault(page = 0, size = 10, sort = "beneficiaryName", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Covered> filteredCovered = coveredService.findPagedCoveredByFilter(pageable, beneficiaryName, beneficiaryCpf, crasName, benefitDeliveryDate);
        return ResponseEntity.ok(filteredCovered);
    }
}
