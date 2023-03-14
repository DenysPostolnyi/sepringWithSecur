package com.Company.FirstSecurityApp.services;

import com.Company.FirstSecurityApp.models.Person;
import com.Company.FirstSecurityApp.repositories.PeopleRepository;
import com.Company.FirstSecurityApp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// сервис для получения данных пользователя из бд
@Service
@Transactional(readOnly = true)
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    // возвращает класс PersonDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person =  peopleRepository.findByUsername(username);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("User not found!"); // ошибка которая видна в браузере
        }

        return new PersonDetails(person.get());
    }
}
