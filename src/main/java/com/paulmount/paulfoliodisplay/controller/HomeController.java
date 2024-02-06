package com.paulmount.paulfoliodisplay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * *  Created by paulm on 2/6/2024
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
