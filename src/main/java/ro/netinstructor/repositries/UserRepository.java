package ro.netinstructor.repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.netinstructor.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
