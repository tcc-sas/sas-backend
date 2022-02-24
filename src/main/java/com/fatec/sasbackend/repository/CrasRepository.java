package com.fatec.sasbackend.repository;

import com.fatec.sasbackend.entity.Cras;
import com.fatec.sasbackend.enums.ECras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrasRepository extends JpaRepository<Cras, Long> {
    Optional<Cras> findByName(ECras name);
}
