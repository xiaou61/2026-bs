package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FamilyMember {
    private Long id;
    private String memberNo;
    private String homeNo;
    private String memberName;
    private String relationType;
    private String phone;
    private String usagePreference;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
