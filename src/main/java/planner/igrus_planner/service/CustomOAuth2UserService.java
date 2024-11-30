package planner.igrus_planner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import planner.igrus_planner.entity.User;
import planner.igrus_planner.repository.UserRepository;

import java.util.Collections;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // OAuth2User 정보 로깅
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        System.out.println("OAuth2User 정보: 이메일=" + email + ", 이름=" + name); // 로그 추가

        // 사용자 데이터베이스에 존재 여부 확인
        User user = userRepository.findByEmail(email);
        if (user == null) {
            // 새 사용자 생성
            user = new User();
            user.setEmail(email);
            user.setUsername(name);
            user.setPassword(""); // OAuth2 로그인 사용자는 비밀번호가 필요하지 않음
            user.setGender("unknown"); // 기본값 설정
            userRepository.save(user);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "email");
    }
}

