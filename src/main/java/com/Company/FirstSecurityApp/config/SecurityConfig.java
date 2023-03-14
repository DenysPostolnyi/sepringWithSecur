package com.Company.FirstSecurityApp.config;

import com.Company.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // конфигурируем сам спринг секьюрити(какая страничка отвечает за логин, какая за ошибки)
        // конфигурируем авторизацию
        httpSecurity.authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN") // дает доступ роли админу без РОЛЕ_
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll() // говорит что можно давать доступ любым пользователям на страницу с логином и ошибкой
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and() // сначала шли настройки доступов к запросам, потом настройки странички логина, енд соединяет эти настройки
                .formLogin() // говорит что конфигурируем форму для логина
                .loginPage("/auth/login") // loginPage("/auth/login") - указывает юрл куда перейти для логинизации
                .loginProcessingUrl("/process_login") // loginProcessingUrl("/process_login") - адрес который принимает данные из формы (action в форме логинизации) и потом будет работать сам с этими данными
                .defaultSuccessUrl("/hello", true) // defaultSuccessUrl("/hello", true) - в случае успешной логинизации перенаправляет на страницу /хеллоу, параметр тру указывает что нужно делать всегда
                .failureUrl("/auth/login?error") // failureUrl("/auth/login?error") - в случае не парвильных данных возвращает на страницу для логина и передает ошибку
                .and()
                .logout().logoutUrl("/logout")// удаление кукес из браузера, что бы выйти из аккаунта, при переходе по этому адресу у юзера стираются куки
                .logoutSuccessUrl("/auth/login"); // куда перенаправить после разлогинивания
//        .csrf().disable()// отключили защиту от межсайтовой подделки запросов
//                .anyRequest().authenticated() // говорит что любые другие запросы только для залогиненых пользователей
    }

    // настраивает аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder()); // передача бина для шифрования пароля
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // данный ретурн показывает что пароль не шифруется. спринг не рад что не шифруем пароль
        return new BCryptPasswordEncoder();
    }

}
