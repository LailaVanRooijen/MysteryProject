package com.mystery.project.entities.hello;

import com.mystery.project.mainconfiguration.Routes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Temporary hello endpoint as per request

@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class HelloController {
    @GetMapping
    public String sayHello(){
        return "Well hello there!";
    }
}
