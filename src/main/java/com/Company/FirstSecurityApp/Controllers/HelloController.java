package com.Company.FirstSecurityApp.Controllers;


import com.Company.FirstSecurityApp.security.PersonDetails;
import com.Company.FirstSecurityApp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String seyHello(){
        return "hello";
    }

    @GetMapping("/showUserInfo") // посылаются куки из сесси по кукам будет доставаться объект из PersonDetails
    public String showUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // обращается в сессию, из сессии достал и поместил в getContext и получаем с помощью getAuthentication
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPage(){
        adminService.doAdminStuff();
        return "admin";
    }

}
