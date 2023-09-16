package com.djachtoma.service;

import com.djachtoma.exception.FacilityNotFoundException;
import com.djachtoma.model.facility.Facility;
import com.djachtoma.model.facility.dto.FacilityDTO;
import com.djachtoma.model.facility.dto.FacilityMapper;
import com.djachtoma.model.physician.dto.PhysicianMapper;
import com.djachtoma.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.djachtoma.utils.ObjectUtils.nullSafeUpdate;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final FacilityMapper mapper;

    public Flux<FacilityDTO> getFacilities() {
        Iterable<FacilityDTO> facilities = Stream.of(facilityRepository.findAll().iterator())
                .map(facility -> mapper.toDTO(facility.next()))
                .collect(Collectors.toSet());
        return Flux.fromIterable(facilities).publish();
    }

    public Mono<FacilityDTO> getFacility(String id) {
        return Mono.just(mapper.toDTO(getFacilityById(id)));
    }

    @Transactional
    public Mono<FacilityDTO> createFacility(FacilityDTO facilityDTO) {
        Facility facility = mapper.toEntity(facilityDTO);
        facilityRepository.save(facility);
        return Mono.just(mapper.toDTO(facility));
    }

    @Transactional
    public Mono<FacilityDTO> updateFacility(String id, FacilityDTO facilityDTO) {
        Facility facility = getFacilityById(id);
        updateFacilityEntity(facility, facilityDTO);
        return Mono.just(mapper.toDTO(facility));
    }

    @Transactional
    public void deleteFacility(String id) {
        Facility facility = getFacilityById(id);
        facilityRepository.delete(facility);
    }


    private void updateFacilityEntity(Facility facility, FacilityDTO facilityDTO) {
        nullSafeUpdate(facilityDTO.getName(), facilityDTO::getName, facility::setName);
        nullSafeUpdate(facilityDTO.getCity(), facilityDTO::getCity, x -> facility.getAddress().setCity(x));
        nullSafeUpdate(facilityDTO.getZipCode(), facilityDTO::getZipCode, x -> facility.getAddress().setZipCode(x));
        nullSafeUpdate(facilityDTO.getAddress(), facilityDTO::getAddress, x -> facility.getAddress().setAddress(x));
        nullSafeUpdate(facilityDTO.getPhysicians(), facilityDTO::getPhysicians, x -> facility.getPhysicians().addAll(x.stream()
                .map(PhysicianMapper::toEntity)
                .collect(Collectors.toSet())));
        facilityRepository.save(facility);
    }


    private Facility getFacilityById(String id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new FacilityNotFoundException(String.format("Facility with provided id: %s does not exist.", id)));
    }
}
