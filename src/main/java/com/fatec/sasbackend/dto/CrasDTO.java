package com.fatec.sasbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CrasDTO {
    private Long id;
    private String name;
}
