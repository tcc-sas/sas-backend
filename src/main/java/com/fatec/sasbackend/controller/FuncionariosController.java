package com.fatec.sasbackend.controller;

import com.fatec.sasbackend.entity.Funcionarios;
import com.fatec.sasbackend.model.FuncionariosDTO;
import com.fatec.sasbackend.service.FuncionariosService;
import com.fatec.sasbackend.service.FuncionariosServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionariosController {

    private final FuncionariosService funcionariosService;

    public FuncionariosController(FuncionariosService funcionariosService){
        this.funcionariosService = funcionariosService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<FuncionariosDTO>> findAllFuncionariosPageable(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<FuncionariosDTO> funcionariosDTOS = funcionariosService.findAllFuncionarios(pageable);

        return ResponseEntity.ok().body(funcionariosDTOS);
    }

}
