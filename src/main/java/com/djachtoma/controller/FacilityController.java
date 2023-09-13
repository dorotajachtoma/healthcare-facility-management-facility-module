package com.djachtoma.controller;

import com.djachtoma.model.facility.dto.FacilityDTO;
import com.djachtoma.service.FacilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/facility")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping
    public Flux<FacilityDTO> getPhysicians() {
        log.info("%s is invoked by: %s", this.getClass().getName() + ".getPhysicians");
        return facilityService.getFacilities();
    }

    @GetMapping("/{id}")
    public Mono<FacilityDTO> getPhysician(@PathVariable String id) {
        log.info("%s is invoked by: %s", this.getClass().getName() + ".getPhysician");
        return facilityService.getFacility(id);
    }

    @PostMapping
    public Mono<FacilityDTO> createPhysician(@RequestBody FacilityDTO facilityDTO) {
        log.info("%s is invoked by: %s", this.getClass().getName() + ".createPhysician");
        return facilityService.createFacility(facilityDTO);
    }

    @PatchMapping("/{id}")
    public Mono<FacilityDTO> updatePhysician(@PathVariable String id, @RequestBody FacilityDTO facilityDTO) {
        log.info("%s is invoked by: %s", this.getClass().getName() + ".updatePhysician");
        return facilityService.updateFacility(id, facilityDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePhysician(@PathVariable String id) {
        log.info("%s is invoked by: %s", this.getClass().getName() + ".deletePhysician");
        facilityService.deleteFacility(id);
    }

}
