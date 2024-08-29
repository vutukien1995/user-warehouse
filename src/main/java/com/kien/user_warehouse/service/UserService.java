package com.kien.user_warehouse.service;

import com.google.gson.Gson;
import com.kien.user_warehouse.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kienvt
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final String location = "export-files";

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

    public String exportFile (List<User> list) throws IOException {
        String path = location + File.separator+"export-file.csv";

        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println("ID,firstname,lastname,middlename,name_suff,dob,address,city,county_name,st,zip," +
                "phone1,aka1fullname,aka2fullname,aka3fullname,StartDat,alt1DOB,alt2DOB,alt3DOB,ssn");

        for (User user : list) {
            printWriter.println(user);
        }
        printWriter.close();

        return path;
    }

}
