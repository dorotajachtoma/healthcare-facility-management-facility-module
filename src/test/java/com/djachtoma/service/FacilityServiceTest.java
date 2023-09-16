package com.djachtoma.service;

import com.djachtoma.exception.FacilityNotFoundException;
import com.djachtoma.model.constant.Specialization;
import com.djachtoma.model.constant.Title;
import com.djachtoma.model.facility.Address;
import com.djachtoma.model.facility.Facility;
import com.djachtoma.model.facility.dto.FacilityDTO;
import com.djachtoma.model.facility.dto.FacilityMapper;
import com.djachtoma.model.physician.Physician;
import com.djachtoma.model.physician.dto.PhysicianDTO;
import com.djachtoma.reference.entity.model.Gender;
import com.djachtoma.reference.entity.model.IDCard;
import com.djachtoma.reference.entity.model.PhoneNumber;
import com.djachtoma.repository.FacilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FacilityServiceTest {

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String CITY = "CITY";
    private static final String ZIP_CODE = "ZIP_CODE";
    private static final String ADDRESS = "ADDRESS";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String ID_CARD_SERIES_NUMBER = "ID_CARD_SERIES_NUMBER";
    private static final LocalDateTime DATE_OF_BIRTH = LocalDateTime.of(2023, 1, 1, 1, 1, 0);


    @InjectMocks
    private FacilityService facilityService;

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private FacilityMapper facilityMapper;


    @Test
    public void getFacilities_shouldReturnAllFacilities() {
        //given
        Set<Facility> facilities = Set.of(getFacility());
        when(facilityRepository.findAll()).thenReturn(facilities);

        //when
        facilityService.getFacilities();

        //then
        verify(facilityRepository, times(1)).findAll();
    }

    @Test
    public void getFacility_shouldReturnFacilityById() {
        //given
        Facility facility = getFacility();
        when(facilityRepository.findById(facility.getId())).thenReturn(Optional.of(facility));

        //when
        facilityService.getFacility(facility.getId());

        //then
        verify(facilityRepository, times(1)).findById(facility.getId());
    }

    @Test
    public void getFacility_shouldThrowExceptionFacilityNotFound() {
        //given
        Facility facility = getFacility();

        //when
        Throwable throwable = catchThrowable(() -> facilityService.getFacility(facility.getId()));

        //then
        assertThat(throwable).isInstanceOf(FacilityNotFoundException.class);
        assertThat(throwable).hasMessageContaining("Facility with provided id: ID does not exist.");
    }

    @Test
    public void createFacility_shouldReturnFacility() {
        //given
        FacilityDTO facilityDTO = getFacilityDTO();
        Facility facility = getFacility();
        when(facilityMapper.toEntity(facilityDTO)).thenReturn(facility);

        //when
        facilityService.createFacility(facilityDTO);

        //then
        verify(facilityRepository, times(1)).save(facility);
    }

    private static Facility getFacility() {
        return Facility.builder()
                .id(ID)
                .name(NAME)
                .address(Address.builder()
                        .city(CITY)
                        .zipCode(ZIP_CODE)
                        .address(ADDRESS)
                        .build())
                .physicians(Set.of(getPhysician()))
                .build();
    }

    private static FacilityDTO getFacilityDTO() {
        return FacilityDTO.builder()
                .id(ID)
                .name(NAME)
                .city(CITY)
                .zipCode(ZIP_CODE)
                .address(ADDRESS)
                .physicians(Set.of(getPhysicianDTO()))
                .build();
    }

    private static PhysicianDTO getPhysicianDTO() {
        return PhysicianDTO.builder()
                .name(NAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .idCardSeriesNumber(ID_CARD_SERIES_NUMBER)
                .gender(Gender.MALE.name())
                .phoneNumber(PHONE_NUMBER)
                .address(ADDRESS)
                .specializations(Set.of(Specialization.EMERGENCY_MEDICINE.name()))
                .title(Title.ACF.name())
                .build();
    }

    private static Physician getPhysician() {
        return Physician.builder()
                .name(NAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .gender(Gender.MALE)
                .idCard(IDCard.builder().build())
                .phoneNumber(PhoneNumber.builder().build())
                .address(com.djachtoma.reference.entity.model.Address.builder().build())
                .specializations(Set.of(Specialization.EMERGENCY_MEDICINE))
                .title(Title.ACF)
                .build();
    }
}
