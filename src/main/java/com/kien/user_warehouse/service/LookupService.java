package com.kien.user_warehouse.service;

import com.kien.user_warehouse.entity.User;
import com.kien.user_warehouse.model.SearchUserResponse;
import com.kien.user_warehouse.model.UserSearchInput;
import com.kien.user_warehouse.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LookupService {

    @Autowired
    UserService userService;

    public List<User> ssn(String ssn) {
        UserSearchInput userSearchInput = new UserSearchInput();
        userSearchInput.setSsn(ssn);
        SearchUserResponse searchUserResponse = userService.callElasticSearchUser(userSearchInput);

        if (searchUserResponse == null)
            return null;

        if (searchUserResponse.getHits().getTotal().getValue() <= 0)
            return null;

        List<User> users = UserUtils.responseToUser(searchUserResponse);
        Set<String> ssnList = getSsnList(users);
        System.out.println(ssnList);

        return users;
    }

    // ============================== PRIVATE FUNCTION ===============================

    private Set<String> getSsnList(List<User> users) {
        Set<String> ssnList = new HashSet<>();
        for (User user : users)
            ssnList.add(user.getSsn());
        return ssnList;
    }

}