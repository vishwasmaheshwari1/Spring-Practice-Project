package com.vmspring.schoolapp.service;


import com.vmspring.schoolapp.constants.AppConstants;
import com.vmspring.schoolapp.model.Person;
import com.vmspring.schoolapp.model.Roles;
import com.vmspring.schoolapp.repository.PersonRepository;
import com.vmspring.schoolapp.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(AppConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person = personRepository.save(person);

        if (null != person && person.getPersonId() > 0) {
            isSaved = true;

        }

        return isSaved;

    }


}
