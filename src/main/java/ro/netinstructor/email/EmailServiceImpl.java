package ro.netinstructor.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmail(EmailDetail details) {
        try {
            SimpleMailMessage email
                    = new SimpleMailMessage();

            email.setFrom(sender);
            email.setTo(details.getRecipient());
            email.setText(details.getMsgBody());
            email.setSubject(details.getSubject());

            javaMailSender.send(email);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
