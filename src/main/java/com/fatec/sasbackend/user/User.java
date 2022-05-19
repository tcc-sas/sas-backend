package com.fatec.sasbackend.user;


import com.fatec.sasbackend.cras.Cras;
import com.fatec.sasbackend.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Builder
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    @NotBlank
    @Size(max = 75)
    private String password;

    @ManyToOne
    @JoinColumn(name = "cras_id", referencedColumnName = "id")
    private Cras cras;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role roles;


}