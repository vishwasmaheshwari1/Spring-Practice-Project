package com.vmspring.schoolapp.controller;

import com.vmspring.schoolapp.model.Courses;
import com.vmspring.schoolapp.model.Person;
import com.vmspring.schoolapp.model.SchoolClass;
import com.vmspring.schoolapp.repository.CoursesRepository;
import com.vmspring.schoolapp.repository.PersonRepository;
import com.vmspring.schoolapp.repository.SchoolClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {


    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    SchoolClassRepository schoolClassRepository;

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {

        //getting all classes information here

        List<SchoolClass> classes = schoolClassRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("schoolClass",new SchoolClass());
        modelAndView.addObject("schoolClasses",classes);
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("schoolClass") SchoolClass schoolClass) {

//        if(errors.hasErrors()) {
//           // return "classes.html";
//        }

        schoolClassRepository.save(schoolClass);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {


        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(id);


        for (Person person : schoolClass.get().getPersons()){
            person.setSchoolClass(null);
            personRepository.save(person);
        }

        schoolClassRepository.deleteById(id);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session, @RequestParam(value = "error", required = false) String error) {


       // List<Person> students = personRepository.findByClassId(classId);

        //Optional<SchoolClass> schoolClass = schoolClassRepository.findById(classId);

        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(classId);

        modelAndView.addObject("schoolClass", schoolClass.get());
        modelAndView.addObject("person", new Person());

        session.setAttribute("schoolClass", schoolClass.get());

        if(error != null) {
            String errorMessage = "Invalid Email Entered!!";
            modelAndView.addObject("errorMessage",errorMessage);
        }

        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {

//        if(errors.hasErrors()) {
//        }
//        Person student = personRepository.readByEmail(person.getEmail());
//        if (student != null) {
//        }
//        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents");
//        return modelAndView;

        ModelAndView modelAndView = new ModelAndView();

        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());

        if (personEntity == null){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId() + "&error=true");
            return modelAndView;
        }

        personEntity.setSchoolClass(schoolClass);
        personRepository.save(personEntity);
        schoolClass.getPersons().add(personEntity);
        schoolClassRepository.save(schoolClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }

    @RequestMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {

         SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
         Optional<Person> person = personRepository.findById(personId);
         person.get().setSchoolClass(null);
         schoolClass.getPersons().remove(person.get());
         SchoolClass newSchoolClass = schoolClassRepository.save(schoolClass);
         session.setAttribute("schoolClass",newSchoolClass);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());

        return modelAndView;

    }


    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {


        //List<Courses> courses = coursesRepository.findAll();

        //dynamic sorting
        List<Courses> courses = coursesRepository.findAll(Sort.by("name").descending());
        //List<Courses> courses = coursesRepository.findAll(Sort.by("name").descending().and(Sort.by("fees")));

        //static sorting
        //List<Courses> courses = coursesRepository.findByOrderByName();
        //List<Courses> courses = coursesRepository.findByOrderByNameDesc();
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Courses());

        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {


        coursesRepository.save(course);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayCourses");

        return modelAndView;
    }

    @RequestMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id, HttpSession session, @RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("course_students.html");

        Optional<Courses> course = coursesRepository.findById(id);

        modelAndView.addObject("courses", course.get());
        modelAndView.addObject("person", new Person());

        session.setAttribute("courses",course.get());

        if(error != null) {
            String errorMessage = "Invalid Email Entered!!";
            modelAndView.addObject("errorMessage",errorMessage);
        }

        return modelAndView;

    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession session) {


        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId() + "&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;

    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId, HttpSession session) {

       Courses courses = (Courses) session.getAttribute("courses");

        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        session.setAttribute("courses", courses);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;


    }
}
