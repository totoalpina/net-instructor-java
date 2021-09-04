package ro.netinstructor.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ro.netinstructor.models.UserDto;
import ro.netinstructor.services.UserService;

import javax.validation.Valid;

@Controller
public class NewAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewAccountController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/cont_nou")
    @PreAuthorize("permitAll()")
    public String newAccount(final Model model){
        model.addAttribute("userDto", new UserDto());
        return "cont_nou";
    }

    @PostMapping("/register")
    public String saveUser(@Valid final UserDto userDto, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Errors in the form : {}", bindingResult);
            model.addAttribute("userDto", userDto);
            return "cont_nou";
        } else {
            userService.save(userDto);
            return "redirect:/index?registered";
        }
    }
}
