package com.fatec.sasbackend.repository;

import com.fatec.sasbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query(
            "SELECT u FROM User u "+
                    "LEFT JOIN u.roles r "+
                    "LEFT JOIN u.cras c "+
                "WHERE "+
                    "(:name = '' OR lower(u.name) = lower(:name)) "+
                    "AND (:cras = '' OR c.id = :cras) "

    )
    List<User> findPagedUsersByFilter(@Param("name") String name,
                                      @Param("cras") String cras,
                                      Pageable pageable);
}
