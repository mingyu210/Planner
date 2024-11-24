package planner.igrus_planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planner.igrus_planner.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
