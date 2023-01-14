package com.vmspring.schoolapp.repository;

import com.vmspring.schoolapp.model.Person;
import com.vmspring.schoolapp.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person readByEmail(String email);
    List<Person> findByClassId(Integer classId);

}
