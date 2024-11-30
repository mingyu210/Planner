
package planner.igrus_planner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // 로그인 페이지를 렌더링 (login.html 파일을 사용)
    }
    @GetMapping("/home")
    public String home() {
        return "home"; // home.html 템플릿 파일
    }
}