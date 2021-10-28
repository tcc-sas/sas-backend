package com.fatec.sasbackend.service;

import com.fatec.sasbackend.model.FuncionariosDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface FuncionariosService {
    Page<FuncionariosDTO> findAllFuncionarios(Pageable pageable);
}
