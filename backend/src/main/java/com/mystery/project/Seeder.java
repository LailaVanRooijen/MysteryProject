package com.mystery.project;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Seeder implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // When creating methods to seed database with mock data, you can call them here
    }
}
