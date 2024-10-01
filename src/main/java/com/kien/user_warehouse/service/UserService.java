package com.kien.user_warehouse.service;

import com.google.gson.Gson;
import com.kien.user_warehouse.entity.User;
import com.kien.user_warehouse.model.Response;
import com.kien.user_warehouse.model.SearchUserRequest;
import com.kien.user_warehouse.model.SearchUserResponse;
import com.kien.user_warehouse.model.UserSearchInput;
import com.kien.user_warehouse.util.RestClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Service
public class UserService {

    @Value("${spring.elasticsearch.uris}")
    private String ELASTIC_URL;
    @Value("${app.user.page.default-size}")
    private Integer DEFAULT_SIZE;
    @Value("${app.export.location}")
    private String LOCATION;
    @Value("${app.user.alias}")
    private String USER_ALIAS;

    public File exportFile(List<User> list) throws IOException {
        File dir = new File(LOCATION + File.separator);
        File file = new File(LOCATION + File.separator + "export-file.txt");
        if (!file.exists()) {
            dir.mkdir();
            file.createNewFile();
            System.out.println("Create file export-file.txt");
        }
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        int i = 1;
        for (User user : list) {
            printWriter.println("SSN Lookup - Person " + i + ":");
            printWriter.println(user);
            i++;
        }
        printWriter.close();

        return file;
    }

    public Response<List<User>> searchUser(UserSearchInput userSearchInput) {
        if (!(StringUtils.hasText(userSearchInput.getFirstname())
                || StringUtils.hasText(userSearchInput.getLastname())
                || StringUtils.hasText(userSearchInput.getAddress())
                || StringUtils.hasText(userSearchInput.getDob())
                || StringUtils.hasText(userSearchInput.getZipcode())
                || StringUtils.hasText(userSearchInput.getSsn())
                || StringUtils.hasText(userSearchInput.getSt()))) {
            return new Response<>(true, 0, new ArrayList<>());
        }

        Gson gson = new Gson();
        verifyInput(userSearchInput);

        String index = USER_ALIAS;

        String url = ELASTIC_URL + "/" + index + "/_search?typed_keys=true&search_type=query_then_fetch";
        Map<String, String> headers = new HashMap<>();
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.setFrom(userSearchInput.getPage());
        searchUserRequest.setSize(userSearchInput.getSize());
        searchUserRequest.setTrack_scores(false);
        searchUserRequest.setVersion(true);

        SearchUserRequest.Query query = new SearchUserRequest.Query();
        SearchUserRequest.Bool bool = new SearchUserRequest.Bool();

        createQueryString(userSearchInput, bool);

        searchUserRequest.setQuery(query);
        query.setBool(bool);

        System.out.println("[Url] " + url);
        System.out.println("[Request] " + gson.toJson(searchUserRequest));
        String response = RestClientUtils.post(url, headers, new Gson().toJson(searchUserRequest));
        System.out.println("[Response] " + response);

        SearchUserResponse searchUserResponse = gson.fromJson(response, SearchUserResponse.class);

        if (searchUserResponse.getHits().getTotal().getValue() <= 0) {
            return new Response<>(true, 0, new ArrayList<>());
        } else {
            int totalPage = searchUserResponse.getHits().getTotal().getValue() / userSearchInput.getSize() + 1;
            return new Response<>(true, totalPage, responseToUser(searchUserResponse));
        }
    }

    public Long count() {
        return 10L;
    }


    // ======================================= PRIVATE FUNCTION =======================================
    private void verifyInput(UserSearchInput userSearchInput) {
        if (Objects.isNull(userSearchInput.getFirstname())) userSearchInput.setFirstname("");
        if (Objects.isNull(userSearchInput.getLastname())) userSearchInput.setLastname("");
        if (Objects.isNull(userSearchInput.getAddress())) userSearchInput.setAddress("");
        if (Objects.isNull(userSearchInput.getDob())) userSearchInput.setDob("");
        if (Objects.isNull(userSearchInput.getZipcode())) userSearchInput.setZipcode("");
        if (Objects.isNull(userSearchInput.getSsn())) userSearchInput.setSsn("");
        if (Objects.isNull(userSearchInput.getSt())) userSearchInput.setSt("");

        if (Objects.isNull(userSearchInput.getPage())) userSearchInput.setPage(0);
        if (Objects.isNull(userSearchInput.getSize())) userSearchInput.setSize(DEFAULT_SIZE);
    }

    private void createQueryString(UserSearchInput userSearchInput, SearchUserRequest.Bool bool) {
        bool.setMust(new ArrayList<>());

        if (StringUtils.hasText(userSearchInput.getFirstname()))
            bool.getMust().add(createMust("firstname", userSearchInput.getFirstname()));

        if (StringUtils.hasText(userSearchInput.getLastname()))
            bool.getMust().add(createMust("lastname", userSearchInput.getLastname()));

        if (StringUtils.hasText(userSearchInput.getAddress()))
            bool.getMust().add(createMust("address", userSearchInput.getAddress()));

        if (StringUtils.hasText(userSearchInput.getDob()))
            bool.getMust().add(createMust("dob", userSearchInput.getDob()));

        if (StringUtils.hasText(userSearchInput.getZipcode()))
            bool.getMust().add(createMust("zip", userSearchInput.getZipcode()));

        if (StringUtils.hasText(userSearchInput.getSsn()))
            bool.getMust().add(createMust("ssn", userSearchInput.getSsn()));

        if (StringUtils.hasText(userSearchInput.getSt()))
            bool.getMust().add(createMust("st", userSearchInput.getSt()));
    }

    private List<User> responseToUser(SearchUserResponse searchUserResponse) {
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

    private SearchUserRequest.Must createMust(String field, String input) {
        SearchUserRequest.Query_string queryString = new SearchUserRequest.Query_string();
        queryString.setFields(List.of(field));
        queryString.setAnalyze_wildcard(true);
        queryString.setQuery(input);

        SearchUserRequest.Must must = new SearchUserRequest.Must();
        must.setQuery_string(queryString);
        return must;
    }

}
