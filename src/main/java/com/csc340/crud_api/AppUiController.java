package com.csc340.crud_api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppUiController {

    @GetMapping({ "", "/", "/home", "/dashboard", })
    public String redirectToStudents() {
        return "redirect:/students";
    }

    @RequestMapping("/403")
    public String _403() {
        return "403";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}