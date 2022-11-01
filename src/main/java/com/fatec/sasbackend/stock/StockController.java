package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.product.ProductDTO;
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
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<StockDTO> stock = stockService.findAllStock(pageable);
        return ResponseEntity.ok().body(stock);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<StockDTO>> findPagedStockByFilter(
            @RequestParam(name = "product") String product,
            @RequestParam(name = "cras") String cras,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<StockDTO> filteredStock = stockService.findPagedStockByFilter(pageable, product, cras);
        return ResponseEntity.ok(filteredStock);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Stock>> registerProduct(@Valid @RequestBody List<StockDTO> dto) {

        List<Stock> stockList = stockService.saveInStock(dto);
        return ResponseEntity.ok().body(stockList);
    }

    @GetMapping("/registration-options")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockRegistrationOptionsDTO> getRegistrationOptions(){
        StockRegistrationOptionsDTO stockRegistrationOptionsDTO = stockService.findRegistrationOptions();

        return ResponseEntity.ok().body(stockRegistrationOptionsDTO);
    }

    @GetMapping("/select-options")
    public ResponseEntity<StockSelectOptions> getSelectOptions(){
        StockSelectOptions selectOptions = stockService.findSelectOptions();
        return ResponseEntity.ok().body(selectOptions);
    }
}
