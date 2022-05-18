package com.fatec.sasbackend.repository;

import com.fatec.sasbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    @Query(
            "SELECT u FROM User u "+
                    "LEFT JOIN u.cras c "+
                "WHERE "+
                    "(:name = '' OR lower(u.name) LIKE CONCAT('%', :name, '%')) "+
                    "AND (:cras = '' OR c.id = :cras) "

    )
    Page<User> findPagedUsersByFilter(@Param("name") String name,
                                      @Param("cras") String cras,
                                      Pageable pageable);

    @Query(
        "SELECT " +
                " CASE " +
                    " WHEN count(u) > 0 THEN TRUE " +
                    " ELSE FALSE " +
                " END " +
                " FROM User u " +
                " WHERE (u.username = :username AND u.id <> :id) "
    )
    Boolean checkIfUsernameAlreadyTakenToUpdate(@Param("username") String username,
                                      @Param("id") Long id);

    @Query(
            "SELECT" +
                    " CASE " +
                        " WHEN count(u) > 0 THEN TRUE" +
                        " ELSE FALSE " +
                    " END " +
                    " FROM User u" +
                    " WHERE (u.username = :username) "

    )
    Boolean checkIfUsernameAlreadyTaken(@Param("username") String username);
}
