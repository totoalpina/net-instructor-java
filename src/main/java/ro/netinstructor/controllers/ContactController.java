package ro.netinstructor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.netinstructor.services.UserService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


@Controller
public class ContactController {

    @Autowired
    private UserService userService;

    @GetMapping("/contact")
    public String sendContact() {
        return "contact";
    }

    @PostMapping("/contactMessage")
    public String sendContactEmail(@RequestParam(name = "contactName") String contactName,
                                   @RequestParam(name = "contactEmail") String contactEmail,
                                   @RequestParam(name = "contactMsg") String contactMsg) {

        try {
            userService.sendContactMail(contactEmail, contactName, contactMsg);
            return "redirect:contact?success";
        } catch (MessagingException e) {
            System.err.print("Mail sending failed");
            return "redirect:contact?fail";
        } catch (UnsupportedEncodingException e) {
            System.err.print("Mail sending failed");
            return "redirect:contact?fail";
        }
    }
}
