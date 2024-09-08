package com.kien.user_warehouse.controller;

import com.kien.user_warehouse.entity.User;
import com.kien.user_warehouse.model.Response;
import com.kien.user_warehouse.model.UserSearchInput;
import com.kien.user_warehouse.repository.UserRepository;
import com.kien.user_warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
        String total_users = String.format("%,d", userRepository.count());
        model.addAttribute("total_users", total_users);
        model.addAttribute("navigation", "Dashboard");
        return "user/dashboard";
    }

    @GetMapping("/user/search")
    public String search(Model model,
                         @ModelAttribute("user") UserSearchInput userSearchInput,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "50") Integer size) {

        Response<List<User>> response = userService.searchUser(userSearchInput);
        model.addAttribute("users", response.getData());
        model.addAttribute("total", response.getTotal());

        model.addAttribute("user", userSearchInput);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        model.addAttribute("navigation", "Search");

        return "user/search";
    }

    @GetMapping(path = "/user/export-file")
    public ResponseEntity<Resource> download(@ModelAttribute("user") UserSearchInput userSearchInput) throws IOException {

        Response<List<User>> response = userService.searchUser(userSearchInput);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export-user.txt");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        File file = userService.exportFile(response.getData());
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
