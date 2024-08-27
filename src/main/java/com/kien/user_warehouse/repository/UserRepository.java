package com.kien.user_warehouse.repository;

import com.kien.user_warehouse.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kienvt
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

    Page<User> findByFirstname(String firstname, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"firstname\": \"?0\"}}]}}")
    Page<User> findByFirstnameUsingCustomQuery(String name, Pageable pageable);

    Page<User> findByFirstnameContainsAndLastnameContainsAndAddressContainsAndDobContainsAndZipContains (
            String firstname, String lastname, String address,
             String dob, String zip, Pageable pageable);

}
