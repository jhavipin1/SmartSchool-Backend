package com.smartSchool.dtos.library;

import com.smartSchool.entities.Rack;
import org.springframework.data.jpa.domain.Specification;

public class RackSpecification {

    public static Specification<Rack> hasRackCode(String rackCode) {
        return (root, query, cb) -> rackCode == null ? null :
                cb.like(cb.lower(root.get("rackCode")), "%" + rackCode.toLowerCase() + "%");
    }

    public static Specification<Rack> hasLocation(String location) {
        return (root, query, cb) -> location == null ? null :
                cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }
}

