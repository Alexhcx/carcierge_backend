package com.carciege.api3.Repositories;

import com.carciege.api3.models.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<CarModel, UUID> {
    //CarModel findCarModelByMarcaIgnoreCase(String marca);
    //CarModel findCarModelByModeloIgnoreCase(String modelo);
    //CarModel findCarModelByAno(int ano);
}
