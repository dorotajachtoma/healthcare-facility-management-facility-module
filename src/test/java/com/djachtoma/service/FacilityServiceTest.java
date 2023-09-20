package com.djachtoma.service;

import com.djachtoma.exception.FacilityNotFoundException;
import com.djachtoma.model.facility.Facility;
import com.djachtoma.model.facility.dto.FacilityDTO;
import com.djachtoma.model.facility.dto.FacilityMapper;
import com.djachtoma.repository.FacilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import java.util.Set;

import static com.djachtoma.util.TestObjectUtil.getFacility;
import static com.djachtoma.util.TestObjectUtil.getFacilityDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FacilityServiceTest {

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
        FacilityDTO facilityDTO = getFacilityDTO();
        when(facilityRepository.findById(facility.getId())).thenReturn(Optional.of(facility));
        when(facilityMapper.toDTO(facility)).thenReturn(facilityDTO);

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
        when(facilityRepository.findById(facility.getId())).thenReturn(Optional.of(facility));
        when(facilityRepository.save(facility)).thenReturn(facility);
        when(facilityMapper.toDTO(facility)).thenReturn(facilityDTO);

        //when
       facilityService.updateFacility(facility.getId(), facilityDTO);

        //then
        verify(facilityRepository, times(1)).save(facility);
    }

    @Test
    public void updateFacility_shouldReturnUpdatedFacility() {
        //given
        FacilityDTO facilityDTO = getFacilityDTO();
        Facility facility = getFacility();
        when(facilityMapper.toEntity(facilityDTO)).thenReturn(facility);
        when(facilityRepository.save(facility)).thenReturn(facility);
        when(facilityMapper.toDTO(facility)).thenReturn(facilityDTO);

        //when
        facilityService.createFacility(facilityDTO);

        //then
        verify(facilityRepository, times(1)).save(facility);
    }

    @Test
    public void updateFacility_shouldThrowException() {
        //given
        FacilityDTO facilityDTO = getFacilityDTO();
        Facility facility = getFacility();
        when(facilityMapper.toEntity(facilityDTO)).thenReturn(facility);
        when(facilityMapper.toDTO(facility)).thenReturn(facilityDTO);

        //when
        Throwable throwable = catchThrowable(() -> facilityService.getFacility(facility.getId()));

        //then
        assertThat(throwable).isInstanceOf(FacilityNotFoundException.class);
        assertThat(throwable).hasMessageContaining("Facility with provided id: ID does not exist.");
    }

    @Test
    public void deleteFacility_shouldDelete() {
        //given
        Facility facility = getFacility();
        when(facilityRepository.findById(facility.getId())).thenReturn(Optional.of(facility));

        //when
        facilityService.deleteFacility(facility.getId());

        //then
        verify(facilityRepository, times(1)).delete(facility);
    }

    @Test
    public void deleteFacility_shouldThrowException() {
        //given
        Facility facility = getFacility();

        //when
        Throwable throwable = catchThrowable(() -> facilityService.getFacility(facility.getId()));

        //then
        assertThat(throwable).isInstanceOf(FacilityNotFoundException.class);
        assertThat(throwable).hasMessageContaining("Facility with provided id: ID does not exist.");
    }



}
