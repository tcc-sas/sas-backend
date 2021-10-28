package com.fatec.sasbackend.service;

import com.fatec.sasbackend.entity.Funcionarios;
import com.fatec.sasbackend.model.FuncionariosDTO;
import com.fatec.sasbackend.repository.FuncionariosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionariosServiceImpl implements FuncionariosService {

    private final FuncionariosRepository repository;

    public FuncionariosServiceImpl(FuncionariosRepository repository){
        this.repository = repository;
    }

    @Override
    public Page<FuncionariosDTO> findAllFuncionarios(Pageable pageable) {
        List<FuncionariosDTO> funcionariosDTOList = repository.findAll(pageable).stream().distinct().map(f -> {
            return FuncionariosDTO.builder()
                    .userId(f.getUserId())
                    .name(f.getName())
                    .username(f.getUsername())
                    .roleName(f.getRoleName())
                    .build();


        }).collect(Collectors.toList());

        return new PageImpl<>(funcionariosDTOList);
    }
}
