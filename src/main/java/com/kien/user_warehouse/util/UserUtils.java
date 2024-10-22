package com.kien.user_warehouse.util;

import com.kien.user_warehouse.entity.User;
import com.kien.user_warehouse.model.SearchUserResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtils {

    public static final String BREAK_LINE = "\r\n";

    public static List<User> responseToUser(SearchUserResponse searchUserResponse) {
        List<User> users = new ArrayList<>();
        for (SearchUserResponse.Hit hit : searchUserResponse.getHits().getHits()) {
            User user = User.builder()
                    .firstname(hit.get_source().getFirstname())
                    .lastname(hit.get_source().getLastname())
                    .middlename(hit.get_source().getMiddlename())
                    .namesuff(hit.get_source().getName_suff())
                    .dob(hit.get_source().getDob())
                    .address(hit.get_source().getAddress())
                    .city(hit.get_source().getCity())
                    .countyname(hit.get_source().getCounty_name())
                    .st(hit.get_source().getSt())
                    .zip(hit.get_source().getZip())
                    .ssn(hit.get_source().getSsn())
                    .build();
            users.add(user);
        }
        return users;
    }

    public static Set<String> getSsnList(List<User> users) {
        Set<String> ssnList = new HashSet<>();
        for (User user : users)
            ssnList.add(user.getSsn());
        return ssnList;
    }

    public static String getUserStr(List<User> users, String ssn) {
        List<User> userList = users
                .stream()
                .filter(u -> ssn.equals(u.getSsn()))
                .collect(Collectors.toList());

        StringBuilder str = new StringBuilder();
        str.append(getHeadUser(userList.get(0)));
        int i = 1;
        for (User user : userList) {
            str.append(getAddressUser(user, i));
            i++;
        }
        return str.toString();
    }


    // ================================= PRIVATE FUNCTION =====================================

    private static String handleNull(String str) {
        return (str == null) ? "" : str;
    }

    private static String getHeadUser(User user) {
        StringBuilder str = new StringBuilder();
        str.append(" First Name: ").append(handleNull(user.getFirstname())).append(BREAK_LINE);
        str.append(" Middle Name: ").append(handleNull(user.getMiddlename())).append(BREAK_LINE);
        str.append(" Last Name: ").append(handleNull(user.getLastname())).append(BREAK_LINE);
        str.append(" Full Name: ").append(handleNull(user.getFirstname())).append(" ")
                .append(handleNull(user.getMiddlename())).append(" ")
                .append(handleNull(user.getLastname())).append(" ").append(BREAK_LINE);
        str.append(" Date Of Birth: ").append(handleNull(user.getDob())).append(BREAK_LINE);
        str.append(" Social Security Number: ").append(handleNull(user.getSsn())).append(BREAK_LINE);
        str.append(" Addresses: ").append(BREAK_LINE);

        return str.toString();
    }

    private static String getAddressUser(User user, int no) {
        StringBuilder str = new StringBuilder();
        str.append("  No. ").append(no).append(BREAK_LINE);
        str.append("   Full Address: ").append(handleNull(user.getAddress())).append(BREAK_LINE);
        str.append("   City: ").append(handleNull(user.getCity())).append(BREAK_LINE);
        str.append("   State Code: ").append(handleNull(user.getSt())).append(BREAK_LINE);
        str.append("   Zip Code: ").append(handleNull(user.getZip())).append(BREAK_LINE);

        return str.toString();
    }

}