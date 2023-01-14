package com.vmspring.schoolapp.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String displayLoginPage(@RequestParam(value="error",required = false) String error,
                                   @RequestParam(value="logout",required = false) String logout,
                                   @RequestParam(value="register",required = false) String register,
                                   Model model){

        String errormsg = null;

        if (error != null) {

            errormsg = "Username or Password is incorrect!";
        }

        else if (logout != null) {

            errormsg = "You have been successflly logged out!";
        }
        else if (register != null) {

            errormsg = "You registration is succesful. Login with registered credentials !!";
        }

        model.addAttribute("errorMessage",errormsg);



        return "login.html";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout((HttpServletRequest) request, (HttpServletResponse) response, auth);
        }

        return "redirect:/login?logout=true";
    }

}
