package planner.igrus_planner.dto;

import lombok.*;

@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestForm {
    private String email;
    private String password;
}
