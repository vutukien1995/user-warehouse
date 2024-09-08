package com.kien.user_warehouse.util;

import com.mashape.unirest.http.Unirest;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class RestClientUtils {

    @SneakyThrows
    public static String get(String url, Map <String, String> mapHeaders, String authorization) {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.putAll(mapHeaders);

        return Unirest.get(url)
                .headers(headers)
                .asString().getBody();
    }

    @SneakyThrows
    public static String post(String url, Map <String, String> mapHeaders, String input){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("accept", "application/json");
        headers.putAll(mapHeaders);

        return Unirest.post(url)
                .headers(headers)
                .body(input)
                .asString().getBody();
    }

//    @SneakyThrows
//    public static String postForm(String url, Map<String, Object> fields, Map <String, String> mapHeaders){
//        return Unirest.post(url)
//                .contentType(ContentType.APPLICATION_FORM_URLENCODED.toString())
//                .fields(fields)
//                .headers(mapHeaders)
//                .asString().getBody();
//    }
//
//    @SneakyThrows
//    public static RestClientResponse post2(String url, Map <String, String> mapHeaders, String input){
//        HttpResponse<String> response = Unirest.post(url)
//                .contentType(kong.unirest.ContentType.APPLICATION_JSON.toString())
//                .body(input)
//                .headers(mapHeaders)
//                .asString();
//
//        return new RestClientResponse(response.getStatus(), response.getBody());
//    }
}

