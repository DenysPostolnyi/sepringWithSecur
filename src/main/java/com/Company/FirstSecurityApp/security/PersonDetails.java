package com.Company.FirstSecurityApp.security;

import com.Company.FirstSecurityApp.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// содержит информацию про аккаунт человека(пароль и тд)
public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }

    // для авторизации (будем получать роли пользователя(какие права он имеет на сайте))
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // если бы мы организовывали авторизацию на списках(привилегий), то нужно было бы в лист помещать объекты new SimpleGrantedAuthority(person.getRole())
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    // возвращает пароль человека
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    // возвращает имя человека
    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    // аккаунт не просрочен
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // аккаунт не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // пароль не просрочен
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
