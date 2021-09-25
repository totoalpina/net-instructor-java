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
import ro.netinstructor.entities.Company;
import ro.netinstructor.models.CompanyDto;
import ro.netinstructor.models.UserDto;
import ro.netinstructor.repositries.CompanyRepository;
import ro.netinstructor.services.CompanyService;
import ro.netinstructor.services.UserService;
import ro.netinstructor.utility.Utilities;

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

    @Autowired
    private CompanyRepository companyRepository;

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
            if (Utilities.verificareCifAnaf(companyDto.getCif(), companyDto.getName())) {
                companyService.registerCompany(companyDto);
                String ciff = companyDto.getCif();
                Company company = companyRepository.findByCif(ciff).get();
                userService.save(userDto, getSiteURL(request), company);
                return "redirect:/index?registered";
            } else {
                return "redirect:/cont_nou?fail";
            }
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
