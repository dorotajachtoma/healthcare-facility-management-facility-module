package com.djachtoma.service;

import com.djachtoma.model.facility.dto.FacilityDTO;
import com.djachtoma.model.facility.dto.FacilityMapper;
import com.djachtoma.model.physician.dto.PhysicianDTO;
import com.djachtoma.repository.FacilityRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Flux;

import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacilityServiceTest {

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String CITY = "CITY";
    private static final String ZIP_CODE = "ZIP_CODE";
    private static final String ADDRESS = "ADDRESS";


    @InjectMocks
    private FacilityService facilityService;

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private FacilityMapper facilityMapper;


    @Test
    public void getFacilities_shouldReturnAllFacilities() {
        //given
        Set<FacilityDTO> facilities = Set.of(getFacilityDTO());

        //when
        when(facilityService.getFacilities()).thenReturn(Flux.fromIterable(facilities));

        //then
        verify(facilityRepository, times(1)).findAll();
    }

    private static FacilityDTO getFacilityDTO() {
        return FacilityDTO.builder()
                .id(ID)
                .name(NAME)
                .city(CITY)
                .zipCode(ZIP_CODE)
                .address(ADDRESS)
                .physicians(Set.of(PhysicianDTO.builder().build()))
                .build();
    }
}
