package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Application {

	public String Gobalname;

	public String getGobalname() {
		return Gobalname;
	}

	public void setGobalname(String gobalname) {
		Gobalname = gobalname;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
