package com.kien.user_warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserRequest {

    private Integer from;
    private Query query;
    private Integer size;
    private Boolean track_scores;
    private Boolean version;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Query {
        private Bool bool;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bool {
        private List<Must> must;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Must {
        private Query_string query_string;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Query_string {
        private Boolean analyze_wildcard;
        private List<String> fields;
        private String query;
    }

}
