package ro.netinstructor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping({"/", "/home", "/index",})
    public String homeController(){
        return "index";
    }
}
