package com.djachtoma.model.facility.dto;

import com.djachtoma.model.facility.Address;
import com.djachtoma.model.facility.Facility;
import com.djachtoma.model.physician.dto.PhysicianMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class FacilityMapper {
    
    public FacilityDTO toDTO(Facility facility) {
        return FacilityDTO.builder()
                .id(facility.getId())
                .name(facility.getName())
                .city(facility.getAddress().getCity())
                .zipCode(facility.getAddress().getZipCode())
                .address(facility.getAddress().getAddress())
                .physicians(Objects.nonNull(facility.getPhysicians()) ? facility.getPhysicians().stream()
                        .map(PhysicianMapper::toDTO)
                        .collect(Collectors.toSet()) : Collections.emptySet())
                .build();
    }

    public Facility toEntity(FacilityDTO dto) {
        return Facility.builder()
                .name(dto.getName())
                .address(Address.builder()
                        .city(dto.getCity())
                        .zipCode(dto.getZipCode())
                        .address(dto.getAddress())
                        .build())
                .physicians(Objects.nonNull(dto.getPhysicians()) ? dto.getPhysicians().stream()
                        .map(PhysicianMapper::toEntity)
                        .collect(Collectors.toSet()) : Collections.emptySet())
                .build();
    }
}
