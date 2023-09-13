package com.djachtoma.model.facility;

import com.djachtoma.model.physician.Physician;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "Facility")
public class Facility {

    @Id
    private String id;
    private String name;
    private Address address;
    private Set<Physician> physicians = new HashSet<>();
}
