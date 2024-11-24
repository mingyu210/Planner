package planner.igrus_planner.dto;


import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
@Getter
@NoArgsConstructor
@Setter
public class UserForm {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String gender;
}
