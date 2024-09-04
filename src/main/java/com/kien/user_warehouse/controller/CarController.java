package com.kien.user_warehouse.controller;

import com.kien.user_warehouse.entity.Car;
import com.kien.user_warehouse.entity.User;
import com.kien.user_warehouse.repository.CarRepository;
import com.kien.user_warehouse.repository.UserRepository;
import com.kien.user_warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kienvt
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping
    public void save(@RequestBody Car car) {
        carRepository.save(car);
    }

    @GetMapping("/{id}")
    public Car findById(@PathVariable String id) {
        return carRepository.findById(id).orElse(null);
    }

    @GetMapping
    public Iterable<Car> findAll() {
        return carRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        carRepository.deleteById(id);
    }

    @PutMapping
    public void update(@RequestBody Car car) {
        carRepository.save(car);
    }

    @GetMapping("/find")
    public List<Car> findByBrand(@RequestParam String brand) {
        return carRepository.findAllByBrand(brand);
    }

}
