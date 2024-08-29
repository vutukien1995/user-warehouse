package com.kien.user_warehouse.controller;

import com.kien.user_warehouse.entity.User;
import com.kien.user_warehouse.model.UserSearchInput;
import com.kien.user_warehouse.repository.UserRepository;
import com.kien.user_warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author kienvt
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user")
    public String index() {
        return "user/index";
    }

    @GetMapping("/user/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("navigation", "Dashboard");
        return "user/dashboard";
    }

    @GetMapping("/user/search")
    public String search(Model model,
                         @ModelAttribute("user") UserSearchInput userSearchInput,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "50") Integer size) {

        if (userSearchInput.getFirstname() == null) userSearchInput.setFirstname("");
        if (userSearchInput.getLastname() == null) userSearchInput.setLastname("");
        if (userSearchInput.getAddress() == null) userSearchInput.setAddress("");
        if (userSearchInput.getDob() == null) userSearchInput.setDob("");
        if (userSearchInput.getZipcode() == null) userSearchInput.setZipcode("");

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage;
        if (StringUtils.hasText(userSearchInput.getFirstname())
                || StringUtils.hasText(userSearchInput.getLastname())
        || StringUtils.hasText(userSearchInput.getAddress()) ) {
            userPage = userRepository.findByFirstnameContainsAndLastnameContainsAndAddressContainsAndDobContainsAndZipContains(
                    userSearchInput.getFirstname(),
                    userSearchInput.getLastname(),
                    userSearchInput.getAddress(),
                    userSearchInput.getDob(),
                    userSearchInput.getZipcode(),
                    pageable);
            model.addAttribute("users", userPage.getContent());
            model.addAttribute("total", userPage.getTotalPages());
            System.out.println("total: " + userPage.getTotalPages());
        }

        model.addAttribute("user", userSearchInput);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        model.addAttribute("navigation", "Search");

        return "user/search";
    }

    @GetMapping(path = "/user/export-file")
    public ResponseEntity<Resource> download(@ModelAttribute("user") UserSearchInput userSearchInput) throws IOException {

        if (Objects.isNull(userSearchInput.getFirstname())) userSearchInput.setFirstname("");
        if (Objects.isNull(userSearchInput.getLastname())) userSearchInput.setLastname("");
        if (Objects.isNull(userSearchInput.getAddress())) userSearchInput.setAddress("");
        if (Objects.isNull(userSearchInput.getDob())) userSearchInput.setDob("");
        if (Objects.isNull(userSearchInput.getZipcode())) userSearchInput.setZipcode("");

        if (Objects.isNull(userSearchInput.getPage())) userSearchInput.setPage(0);
        if (Objects.isNull(userSearchInput.getSize())) userSearchInput.setSize(50);

        List<User> userList = new ArrayList<>();
        if (StringUtils.hasText(userSearchInput.getFirstname())
                || StringUtils.hasText(userSearchInput.getLastname())
                || StringUtils.hasText(userSearchInput.getAddress())
                || StringUtils.hasText(userSearchInput.getDob())
                || StringUtils.hasText(userSearchInput.getZipcode())) {
            Pageable pageable = PageRequest.of(userSearchInput.getPage(), userSearchInput.getSize());
            Page<User> userPage;
            userPage = userRepository.findByFirstnameContainsAndLastnameContainsAndAddressContainsAndDobContainsAndZipContains(
                    userSearchInput.getFirstname(),
                    userSearchInput.getLastname(),
                    userSearchInput.getAddress(),
                    userSearchInput.getDob(),
                    userSearchInput.getZipcode(),
                    pageable);

            userList = userPage.getContent();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export-user.csv");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        File file = new File(userService.exportFile(userList));
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
