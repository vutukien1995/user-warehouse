package com.kien.user_warehouse.controller;

import com.kien.user_warehouse.entity.User;
import com.kien.user_warehouse.model.UserSearchInput;
import com.kien.user_warehouse.repository.UserRepository;
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

/**
 * @author kienvt
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

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
    public ResponseEntity<Resource> download(String param) throws IOException {

//        File file = new File(SERVER_LOCATION + File.separator + image + EXTENSION);
        File file = new File("/Users/kienvt/lab/user-warehouse/src/main/resources/static/assets/img/ivancik.jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export-user.jpg");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
