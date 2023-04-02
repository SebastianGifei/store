package com.poc.store.repository;

import com.poc.store.model.database.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    Manufacturer findByManufacturerName(@Param("manufacturer_name") String manufacturerName);
}
