package ro.netinstructor.services;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.netinstructor.entities.Company;
import ro.netinstructor.entities.User;
import ro.netinstructor.enums.UserRole;
import ro.netinstructor.models.UserDto;
import ro.netinstructor.repositries.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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




    public void register(User user, String siteURL) {

    }

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

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "cosmin@zenic.ro";
        String senderName = "Net-Instructor";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void sendContactMail(String contactEmail, String contactName, String contactMsg)
            throws MessagingException, UnsupportedEncodingException {

        String toAddress = "totoalpina@gmail.com";
        String fromAddress = contactEmail;
        String senderName = contactName;
        String subject = "Contact form Net-Instructor.ro";
        String content = contactMsg;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content);

        mailSender.send(message);
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
