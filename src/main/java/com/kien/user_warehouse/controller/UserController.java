package com.kien.user_warehouse.controller;

import com.kien.user_warehouse.model.User;
import com.kien.user_warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/user/search")
    public String search(Model model,
                         @RequestParam(value = "firstname", defaultValue = "") String firstname,
                         @RequestParam(value = "lastname", defaultValue = "") String lastname,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "50") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage;
        if (StringUtils.hasText(firstname)) {
            userPage = userRepository.findByFirstnameLikeAndLastNameLike(firstname, lastname, pageable);
            model.addAttribute("users", userPage.getContent());
        }

        model.addAttribute("firstname", firstname);
        model.addAttribute("lastname", lastname);

        return "user/search";
    }

}
