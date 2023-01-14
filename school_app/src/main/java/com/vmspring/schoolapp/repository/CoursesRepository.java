package com.vmspring.schoolapp.repository;

import com.vmspring.schoolapp.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//to change path of this repository as rest api using srping data rest
@RepositoryRestResource(path = "courses")
//to not to expose this repository as rest api using srping data rest
//@RepositoryRestResource(path = "courses", exported = false)
//@RepositoryRestResource(exported = false)
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

        List<Courses> findByOrderByNameDesc();


        List<Courses> findByOrderByName();
}
