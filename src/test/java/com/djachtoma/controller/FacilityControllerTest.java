package com.djachtoma.controller;

import com.djachtoma.configuration.RedisContainerSetup;
import com.djachtoma.configuration.TestSetup;
import com.djachtoma.model.constant.Specialization;
import com.djachtoma.model.constant.Title;
import com.djachtoma.model.facility.dto.FacilityDTO;
import com.djachtoma.model.physician.dto.PhysicianDTO;
import com.djachtoma.reference.entity.model.Gender;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Set;

import static com.djachtoma.util.TestObjectUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FacilityControllerTest extends TestSetup {

    private static final String NAME = "NAME";
    private static final String CITY = "CITY";
    private static final String ZIP_CODE = "ZIP_CODE";
    private static final String STREET = "STREET";

    @Autowired
    private WebTestClient client;

    private RedisContainerSetup redisContainerSetup;

    @BeforeEach
    public void setup() {
        redisContainerSetup.start();
    }

    @Test
    public void getAllFacilitiesShouldReturnAllFacility() {
        //given
        FacilityDTO facilityDTO = getFacilityDTO();
        this.client.post()
                .uri("/api/facility")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(facilityDTO)
                .exchange()
                .expectBody(FacilityDTO.class)
                .returnResult()
                .getResponseBody();

        //when
        List<FacilityDTO> result = this.client.get()
                .uri("/api/facility")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(FacilityDTO.class)
                .returnResult()
                .getResponseBody();

        //then
        FacilityDTO created = result.get(0);
        assertThat(created.getName()).isEqualTo(facilityDTO.getName());
        assertThat(created.getAddress()).isEqualTo(facilityDTO.getAddress());
        assertThat(created.getCity()).isEqualTo(facilityDTO.getCity());
        assertThat(created.getZipCode()).isEqualTo(facilityDTO.getZipCode());


    }

    @Test
    public void getFacilityShouldReturnFacilityDTO() {
        //given
        FacilityDTO facilityDTO = getFacilityDTO();
        FacilityDTO created = this.client.post()
                .uri("/api/facility")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(facilityDTO)
                .exchange()
                .expectBody(FacilityDTO.class)
                .returnResult()
                .getResponseBody();

        //when
        this.client.get()
                .uri("/api/facility/" + created.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(FacilityDTO.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(created.getName()).isEqualTo(facilityDTO.getName());
        assertThat(created.getAddress()).isEqualTo(facilityDTO.getAddress());
        assertThat(created.getCity()).isEqualTo(facilityDTO.getCity());
        assertThat(created.getZipCode()).isEqualTo(facilityDTO.getZipCode());
    }

    @Test
    public void createFacilityShouldReturnFacilityDTO() {
        //given
        FacilityDTO facilityDTO = getFacilityDTO();

        //when
        FacilityDTO created = this.client.post()
                .uri("/api/facility")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(facilityDTO)
                .exchange()
                .expectBody(FacilityDTO.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(created.getName()).isEqualTo(facilityDTO.getName());
        assertThat(created.getAddress()).isEqualTo(facilityDTO.getAddress());
        assertThat(created.getCity()).isEqualTo(facilityDTO.getCity());
        assertThat(created.getZipCode()).isEqualTo(facilityDTO.getZipCode());
    }

    @Test
    public void updateFacilityShouldReturnFacilityDTO() {
        //given
        FacilityDTO facilityDTO = getFacilityDTO();
        FacilityDTO created = this.client.post()
                .uri("/api/facility")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(facilityDTO)
                .exchange()
                .expectBody(FacilityDTO.class)
                .returnResult()
                .getResponseBody();

        //when
        facilityDTO.setCity("Warsaw");

        FacilityDTO result = this.client.patch()
                .uri("/api/facility/" + created.getId())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(created)
                .exchange()
                .expectBody(FacilityDTO.class)
                .returnResult()
                .getResponseBody();
        //then
        assertThat(result).isEqualTo(created);
    }

    @Test
    public void deleteFacilityShouldDeleteFacility() {
        //given
        FacilityDTO facilityDTO = getFacilityDTO();
        FacilityDTO created = this.client.post()
                .uri("/api/facility")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(facilityDTO)
                .exchange()
                .expectBody(FacilityDTO.class)
                .returnResult()
                .getResponseBody();

        //when
        this.client.delete()
                .uri("/api/facility/" + created.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    private static FacilityDTO getFacilityDTO() {
        return FacilityDTO.builder()
                .name(NAME)
                .city(CITY)
                .zipCode(ZIP_CODE)
                .address(STREET)
                .physicians(Set.of(getPhysicianDTO()))
                .build();
    }

    private static PhysicianDTO getPhysicianDTO() {
        return PhysicianDTO.builder()
                .name(NAME)
                .surname(SURNAME)
                .gender(Gender.MALE.name())
                .idCardSeriesNumber(ID_CARD_SERIES_NUMBER)
                .phoneNumber(PHONE_NUMBER)
                .dateOfBirth(DATE_OF_BIRTH)
                .address(ADDRESS)
                .city(CITY)
                .zipCode(ZIP_CODE)
                .title(Title.CON.name())
                .specializations(Set.of(Specialization.EMERGENCY_MEDICINE.name(), Specialization.OPHTHALMOLOGY.name()))
                .build();

    }

}
