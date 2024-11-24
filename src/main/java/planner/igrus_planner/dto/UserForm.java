package planner.igrus_planner.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
@Getter
@NoArgsConstructor
public class UserForm {
    private String username;
    private String password;
    private String email;
    private LocalDate birthDate;
    private String gender;
}
