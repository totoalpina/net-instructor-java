package ro.netinstructor.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ro.netinstructor.models.CompanyDto;
import ro.netinstructor.models.UserDto;
import ro.netinstructor.services.CompanyService;
import ro.netinstructor.services.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
public class NewAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewAccountController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/cont_nou")
    @PreAuthorize("permitAll()")
    public String newAccount(final Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("companyDto", new CompanyDto());
        return "cont_nou";
    }

    @PostMapping("/register")
    public String saveUser(@Valid final UserDto userDto, @Valid final CompanyDto companyDto, final BindingResult bindingResult, HttpServletRequest request, final Model model)
            throws UnsupportedEncodingException, MessagingException {
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Errors in the form : {}", bindingResult);
            model.addAttribute("userDto", userDto);
            model.addAttribute("companyDto", companyDto);
            return "cont_nou";
        } else {
            userService.save(userDto, getSiteURL(request));
            companyService.registerCompany(companyDto);
            return "redirect:/index?registered";
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "redirect:index?verify_success";
        } else {
            return "redirect:index?verify_fail";
        }
    }
}
