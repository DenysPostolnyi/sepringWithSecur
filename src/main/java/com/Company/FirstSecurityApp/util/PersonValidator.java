package com.Company.FirstSecurityApp.util;

import com.Company.FirstSecurityApp.models.Person;
import com.Company.FirstSecurityApp.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Controller
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(peopleService.getPersonByName(person.getUsername()).isPresent()){
            errors.rejectValue("username", "", "User with this username already exists");
        }
    }
}
