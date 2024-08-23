package com.kien.user_warehouse.repository;

import com.kien.user_warehouse.model.Car;
import com.kien.user_warehouse.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author kienvt
 */
public interface CarRepository extends ElasticsearchRepository<Car, String> {

    List<Car> findAllByBrand(String brand);

}
