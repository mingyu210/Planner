package planner.igrus_planner.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import planner.igrus_planner.dto.LoginRequestForm;
import planner.igrus_planner.dto.UserForm;
import planner.igrus_planner.entity.User;
import planner.igrus_planner.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserForm userForm) {
        userService.registerUser(userForm);
        return "success";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequestForm loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // 로그인 처리
        userService.loginUser(email, password);
        return "success";
    }

}
