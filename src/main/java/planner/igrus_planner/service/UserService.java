package planner.igrus_planner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planner.igrus_planner.dto.UserForm;
import planner.igrus_planner.entity.User;
import planner.igrus_planner.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserForm userForm) {
        if(userRepository.findByEmail(userForm.getEmail()) != null) {
            throw new IllegalArgumentException("Email already taken");
        }
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setEmail(userForm.getEmail());
        user.setBirthDate(userForm.getBirthDate());
        user.setGender(userForm.getGender());

        userRepository.save(user);

    }

    public void loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

    }
}
//
