package com.vmspring.schoolapp.controller;

import com.vmspring.schoolapp.model.Person;
import com.vmspring.schoolapp.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;


    //binding properties defined in application.properties file
    @Value("${school.pageSize}")
    private int defaultPageSize;

    @Value("${school.contact.successMsg}")
    private String message;


    @Autowired
    Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {

        Person person = personRepository.readByEmail(authentication.getName());

        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());

        if (person.getSchoolClass() != null && null != person.getSchoolClass().getName()) {
            model.addAttribute("enrolledClass", person.getSchoolClass().getName());
        }

        session.setAttribute("loggedInPerson", person);

        //throw new RuntimeException("Application Error");
        logMessages();
        return "dashboard.html";
    }

    private void logMessages() {
        log.error("Error message from the Dashboard page");
        log.warn("Warning message from the Dashboard page");
        log.info("Info message from the Dashboard page");
        log.debug("Debug message from the Dashboard page");
        log.trace("Trace message from the Dashboard page");


        log.error("defaultPageSize value with @Value annotation is : "+defaultPageSize);
        log.error("successMsg value with @Value annotation is : "+message);

        log.error("defaultPageSize value with Environment is : "+environment.getProperty("school.pageSize"));
        log.error("successMsg value with Environment is : "+environment.getProperty("school.contact.successMsg"));
        log.error("Java Home environment variable using Environment is : "+environment.getProperty("JAVA_HOME"));
    }


}
