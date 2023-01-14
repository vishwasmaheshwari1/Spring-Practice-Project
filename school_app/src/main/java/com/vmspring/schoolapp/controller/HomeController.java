package com.vmspring.schoolapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {"", "/", "home", "home/myhome"})
    //@RequestMapping(value = {"home", "home/myhome"})
    public String displayHomePage(Model model) {
        model.addAttribute("username","Baba Yaga");
        return "home.html";
    }

}
