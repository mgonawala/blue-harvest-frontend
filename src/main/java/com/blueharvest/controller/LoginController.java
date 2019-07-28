package com.blueharvest.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/accounts";
        }
        return "/login";
    }

    @GetMapping("/table")
    public ModelAndView table() {

        ModelAndView modelAndView = new ModelAndView();
        //modelAndView.addObject("products", products);

        modelAndView.setViewName("/table");
        return modelAndView;

    }

}
