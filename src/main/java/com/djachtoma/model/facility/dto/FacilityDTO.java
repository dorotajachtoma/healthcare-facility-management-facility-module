package com.djachtoma.model.facility.dto;

import com.djachtoma.model.physician.dto.PhysicianDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDTO {

    private String id;
    private String name;
    private String city;
    private String zipCode;
    private String address;
    private Set<PhysicianDTO> physicians;
}
