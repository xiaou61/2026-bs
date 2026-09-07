package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignerProfile {
    private Long id;
    private String signerNo;
    private String signerName;
    private String signerType;
    private String certificateNo;
    private String authorizationStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



