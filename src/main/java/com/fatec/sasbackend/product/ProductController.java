package com.fatec.sasbackend.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        Page<ProductDTO> userDTO = productService.findAllProducts(pageable);
        return ResponseEntity.ok().body(userDTO);
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

        ProductDTO userDTO = productService.registerProduct(dto);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> findProductById(
            @PathVariable Long userId) {

        ProductDTO userDTO = productService.findProductById(userId);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO userDto) {
        ProductDTO user = productService.updateProduct(userDto);
        return ResponseEntity.ok().body(user);
    }

    //@TODO
    //@DeleteMapping("/{id}")
}
