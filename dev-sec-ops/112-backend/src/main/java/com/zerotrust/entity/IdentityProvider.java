package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class IdentityProvider {
    private Long id;
    private String providerName;
    private String providerCode;
    private String syncMode;
    private String endpointUrl;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
