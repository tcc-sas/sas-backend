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
        @UniqueConstraint(columnNames = "beneficiary_id")
})
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 45)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Column(name = "beneficiary_id")
    private String beneficiaryId;

    private String adress;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "cras_id", referencedColumnName = "id")
    private Cras cras;

}
