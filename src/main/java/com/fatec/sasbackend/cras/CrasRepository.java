package com.fatec.sasbackend.cras;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrasRepository extends JpaRepository<Cras, Long> {
}
