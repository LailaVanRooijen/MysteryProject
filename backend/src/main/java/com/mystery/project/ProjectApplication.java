package com.mystery.project;

import com.mystery.project.mainconfiguration.CustomBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

 import javax.swing.*;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ProjectApplication.class);
    	application.setBanner(new CustomBanner());
		application.run(args);
	}

}
