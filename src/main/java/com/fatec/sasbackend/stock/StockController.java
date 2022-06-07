package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<StockDTO>> findAllPagedStock(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<StockDTO> userDTO = stockService.findAllStock(pageable);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<StockDTO>> findPagedStockByFilter(
            @RequestParam(name = "productId") String productId,
            @RequestParam(name = "name") String name,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<StockDTO> filteredStock = stockService.findPagedStockByFilter(pageable, productId, name);
        return ResponseEntity.ok(filteredStock);
    }


    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StockDTO>> registerProduct(@Valid @RequestBody List<StockDTO> dto) {

        List<StockDTO> userDTO = stockService.saveInStock(dto);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping("/products-to-add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Product>> productsToAdd(){
        return null;
    }

}
