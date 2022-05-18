package com.fatec.sasbackend.entity;

import com.fatec.sasbackend.converter.LocalDateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "beneficiary", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf")
})
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 70)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String rg;

    @NotBlank
    @Size(max = 15)
    private String cpf;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "zip_code")
    private String zipCode;

    private String adress;

    @Column(name = "house_number")
    private Integer houseNumber;

    private String district;

    private String city;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "cras_id", referencedColumnName = "id")
    private Cras cras;

}
