package com.fatec.sasbackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(	name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 45)
    private String name;

    @NotBlank
    @Size(max = 45)
    private String username;

    @NotBlank
    @Size(max = 75)
    private String password;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cras_id", referencedColumnName = "id")
    private Cras cras;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Role roles;




}