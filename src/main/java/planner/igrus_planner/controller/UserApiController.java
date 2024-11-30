package planner.igrus_planner.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import planner.igrus_planner.dto.LoginRequestForm;
import planner.igrus_planner.dto.UserForm;
import planner.igrus_planner.entity.User;
import planner.igrus_planner.service.UserService;

@Slf4j
@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/users/register")
    public String registerUser(@RequestBody UserForm userForm) {
        userService.registerUser(userForm);
        return "success";
    }

    @PostMapping("/api/users/login")
    public String loginUser(@RequestBody LoginRequestForm loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // 로그인 처리
        userService.loginUser(email, password);
        return "success";
    }
    @GetMapping("/oauth2/success")
    public String handleOAuth2Login(@AuthenticationPrincipal OAuth2User oAuth2User) {
        // OAuth2User에서 이메일, 이름 등 필요한 정보를 추출
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        log.info("OAuth2 로그인 성공! 이메일: " + email + ", 이름: " + name);

        // 이메일을 기준으로 사용자 DB에 존재하는지 확인
        User existingUser = userService.findByEmail(email);
        if (existingUser == null) {
            // 사용자 정보가 없으면 새로 회원가입 처리
            UserForm newUserForm = new UserForm();
            newUserForm.setUsername(name);  // 이름을 username으로 설정 (원하는 방식으로 처리)
            newUserForm.setEmail(email);
            newUserForm.setPassword("google_oauth2");  // 구글 OAuth2 로그인 시 패스워드는 필요없지만, 예시로 설정
            newUserForm.setGender("unknown");  // 기본 값 설정 (구글에서 가져올 수 있는 경우 사용)
            newUserForm.setBirthDate("");  // 기본 값 설정

            userService.registerUser(newUserForm);  // 일반 회원가입과 동일하게 처리
            log.info("새로운 사용자로 회원가입 완료: " + email);
        } else {
            log.info("기존 사용자 로그인: " + email);
        }

        // 로그인 후 리다이렉트 혹은 사용자 정보 표시
        return "OAuth2 로그인 성공! 이메일: " + email + ", 이름: " + name;
    }



}
