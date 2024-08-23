package com.kien.user_warehouse.repository;

import com.kien.user_warehouse.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kienvt
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

    Page<User> findByFirstname(String firstname, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"firstname\": \"?0\"}}]}}")
    Page<User> findByFirstnameUsingCustomQuery(String name, Pageable pageable);

    Page<User> findByFirstnameLikeAndLastNameLike(String firstname, String lastname, Pageable pageable);

}
