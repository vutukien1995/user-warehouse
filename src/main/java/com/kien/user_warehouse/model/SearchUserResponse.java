package com.kien.user_warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kienvt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserResponse {

    private Integer took;
    private Boolean timed_out;
    private Shards _shards;
    private Hits hits;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Shards {
        private Integer total;
        private Integer successful;
        private Integer skipped;
        private Integer failed;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Hits {
        private Total total;
        private String max_score;
        private List<Hit> hits;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Total {
        private Integer value;
        private String relation;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Hit {
        private String _index;
        private String _id;
        private String _version;
        private String _score;
        private Source _source;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Source {
        private String firstname;
        private String lastname;
        private String middlename;
        private String name_suff;
        private String dob;
        private String address;
        private String city;
        private String county_name;
        private String st;
        private String zip;
        private String ssn;
        private String message;
    }

}
