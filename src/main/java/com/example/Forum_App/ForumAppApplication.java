//indicates the location of the class within the project structure
package com.example.Forum_App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//annotation that marks the class as a source of bean definitions. auto-config mechanism. tells spring to scan for
//components, configs and services in the package
@SpringBootApplication
public class ForumAppApplication {

	//main app, entry point
	public static void main(String[] args) {

		SpringApplication.run(ForumAppApplication.class, args);
	}

}
