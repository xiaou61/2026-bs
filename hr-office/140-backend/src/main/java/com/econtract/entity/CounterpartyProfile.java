package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CounterpartyProfile {
    private Long id;
    private String counterpartyNo;
    private String counterpartyName;
    private String creditCode;
    private String contactName;
    private String contactPhone;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



