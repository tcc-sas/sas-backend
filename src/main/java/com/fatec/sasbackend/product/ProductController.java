package com.fatec.sasbackend.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProductDTO>> findAllPagedProducts(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ProductDTO> productDTO = productService.findAllProducts(pageable);
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProductDTO>> findPagedProductsByFilter(
            @RequestParam(name = "productId") String productId,
            @RequestParam(name = "name") String name,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<ProductDTO> filteredProducts = productService.findPagedProductsByFilter(pageable, productId, name);
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> registerProduct(@Valid @RequestBody ProductDTO dto) {

        ProductDTO productDTO = productService.registerProduct(dto);
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> findProductById(
            @PathVariable Long userId) {

        ProductDTO productDTO = productService.findProductById(userId);
        return ResponseEntity.ok().body(productDTO);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO userDto) {
        ProductDTO productDTO = productService.updateProduct(userDto);
        return ResponseEntity.ok().body(productDTO);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteProduct(@RequestParam String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product-beneficiary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SimpleProductDTO>> findProductsForBeneficiary() {
         List<SimpleProductDTO> productDTOS = productService.findProductsForBeneficiary();
        return ResponseEntity.ok().body(productDTOS);
    }
}
