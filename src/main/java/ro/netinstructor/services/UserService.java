package ro.netinstructor.services;

import ro.netinstructor.entities.User;
import ro.netinstructor.models.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> findById(Long id);

    Optional<UserDto> findByEmail(String email);

    User save(UserDto userDto);
}
