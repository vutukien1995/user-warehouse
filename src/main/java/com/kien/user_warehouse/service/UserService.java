package com.kien.user_warehouse.service;

import com.kien.user_warehouse.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author kienvt
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final ElasticsearchOperations esTemplate;

    public SearchHits<User> searchUser(String name,
                                       Pageable pageable) {
        CriteriaQuery query = buildSearchQuery(name);
        query.setPageable(pageable);

        return esTemplate.search(query, User.class);
    }

    private CriteriaQuery buildSearchQuery(String name) {
        var criteria = new Criteria();
        if (StringUtils.hasText(name)) {
            criteria.and(new Criteria("firstname").contains(name));
        }

        return new CriteriaQuery(criteria);
    }

}
