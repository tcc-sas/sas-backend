package com.fatec.sasbackend.repository;

import com.fatec.sasbackend.entity.Cras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrasRepository extends JpaRepository<Cras, Long> {
}
