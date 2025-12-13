package com.xiaou.seniorhealth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ElderCreateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String gender;
    @NotNull
    private LocalDate birthDate;
    @NotBlank
    private String phone;
    private String address;
    private String emergencyContact;
}
