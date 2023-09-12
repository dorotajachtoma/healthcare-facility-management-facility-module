package com.djachtoma.repository;

import com.djachtoma.model.facility.Facility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends CrudRepository<Facility, String> {
}
