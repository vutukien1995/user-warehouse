package com.kien.user_warehouse.controller;

import com.kien.user_warehouse.service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lookup")
public class LookupApi {

    @Autowired
    LookupService lookupService;

    @GetMapping("/ssn")
    public Object ssn(@RequestParam String ssn) {
        return lookupService.ssn(ssn);
    }

}
