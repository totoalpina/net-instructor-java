package ro.netinstructor.services;

import org.springframework.data.jpa.repository.Query;
import ro.netinstructor.entities.Company;
import ro.netinstructor.entities.User;
import ro.netinstructor.models.UserDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> findById(Long id);

    Optional<UserDto> findByEmail(String email);

    User save(UserDto userDto, String siteUrl, Company company) throws UnsupportedEncodingException, MessagingException;

    User findByVerificationCode(String code);

    boolean verify(String verificationCode);

    void sendContactMail(String contactEmail, String contactName, String contactMsg)throws MessagingException, UnsupportedEncodingException;
}
