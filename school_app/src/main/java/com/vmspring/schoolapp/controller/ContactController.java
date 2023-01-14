package com.vmspring.schoolapp.controller;


import com.vmspring.schoolapp.model.Contact;
import com.vmspring.schoolapp.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ContactController {


    private static Logger log = LoggerFactory.getLogger(ContactController.class);


    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

//    @RequestMapping("/contact")
//    public String displayContactPage() {
//
//        //need tell their exist a form which requires bean validation
//
//
//
//        return "contact.html";
//
//    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model) {

        //need tell their exist a form which requires bean validation
        model.addAttribute("contact",new Contact());


        return "contact.html";

    }


//    //@PostMapping(value="/saveMessage")
//    @RequestMapping(value="/saveMessage", method = POST)
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobile) {
//
//        log.info("name : " + name);
//        log.info("mobile : " + mobile);
//
//        //if want to send some data along with View then ise ModelandView
//        //else string html file name can returned
//        return new ModelAndView("redirect:/contact");
//
//    }
//    @RequestMapping(value="/saveMessage", method = POST)
//    public ModelAndView saveMessage(Contact contact) {
//
////        names are supposed to match in frontend (form) and in backend (pojo class parameters)
//
//        //contact will be used by contact service to store data in db
//
//        //Controller layer (business logic) -> Service Layer -> persistant (data/repository) layer
//
//        contactService.saveMessageDetails(contact);
//        return new ModelAndView("redirect:/contact");
//    }


    @RequestMapping(value="/saveMessage", method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {

//        names are supposed to match in frontend (form) and in backend (pojo class parameters)

        //contact will be used by contact service to store data in db

        //Controller layer (business logic) -> Service Layer -> persistant (data/repository) layer

        if (errors.hasErrors()) {
            log.error("Contact form validation failed due to: " + errors.toString());
            return "contact.html";
        }

        contactService.saveMessageDetails(contact);




//        contactService.setCounter(contactService.getCounter() + 1);
//
//        log.info("Number of times the Contact form is submitted : " + contactService.getCounter());

        return "redirect:/contact";
    }



//    @RequestMapping("/displayMessages")
//    public ModelAndView displayMessages(Model model) {
//        List<Contact> contactMsgs = contactService.findMsgsWithOpenStatus();
//        ModelAndView modelAndView = new ModelAndView("messages.html");
//        modelAndView.addObject("contactMsgs",contactMsgs);
//        return modelAndView;
//    }

    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {

        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum, sortField, sortDir);

        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", msgPage.getTotalPages());
        model.addAttribute("totalMsgs", msgPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg",method = GET)
    public String closeMsg(@RequestParam int id, Authentication authentication) {
    //public String closeMsg(@RequestParam int id) {
        contactService.updateMsgStatus(id,authentication.getName());
        return "redirect:/displayMessages";
    }

}
