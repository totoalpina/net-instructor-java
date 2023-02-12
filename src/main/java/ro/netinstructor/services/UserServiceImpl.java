package ro.netinstructor.services;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.netinstructor.email.EmailDetail;
import ro.netinstructor.email.EmailService;
import ro.netinstructor.entities.Company;
import ro.netinstructor.entities.User;
import ro.netinstructor.enums.UserRole;
import ro.netinstructor.models.UserDto;
import ro.netinstructor.repositries.UserRepository;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Override
    public Optional<UserDto> findById(final Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getEmail(), user.getFirstName(), user.getLastName()));
    }

    @Override
    public Optional<UserDto> findByEmail(final String email) {
        return userRepository.findByEmail(email)
                .map(user -> new UserDto(user.getEmail(), user.getFirstName(), user.getLastName()));
    }

    public User save(UserDto userDto, String siteUrl, Company company)
            throws UnsupportedEncodingException, MessagingException {
        String randomCode = RandomString.make(64);

        User user = new User(userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getFirstName(),
                userDto.getLastName(),
                UserRole.USER,
                company);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        sendVerificationEmail(user, siteUrl);
        return userRepository.save(user);
    }

    @Override
    public User findByVerificationCode(String code) {
        return userRepository.findByVerificationCode(code);
    }

    private void sendVerificationEmail(User user, String siteURL) {
        String toAddress = user.getEmail();
        String subject = "Va rugam sa va verificati inregistrarea pe site-ul Net-Instructor.ro";
        String content = "Dear [[name]],<br>"
                + "Accesati link-ul de mai jos pentru a va verifica integistrarea:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Va multumim,<br>"
                + "NET-Instructor.ro";
        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        EmailDetail email = new EmailDetail(toAddress, content, subject);
        emailService.sendEmail(email);
    }

    @Override
    public void sendContactMail(String contactEmail, String contactName, String contactMsg) {

        String toAddress = "cosminbondoi@gmail.com";
        String subject = "Contact from Net-Instructor.ro";
        String content = contactMsg
                + "\n \n Sender name: "
                + contactName
                + "\n email address:  "
                + contactEmail;

        EmailDetail email = new EmailDetail(toAddress, content, subject);
        emailService.sendEmail(email);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }
}
