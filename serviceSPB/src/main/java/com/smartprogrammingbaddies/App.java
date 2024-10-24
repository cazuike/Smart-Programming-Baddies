package com.smartprogrammingbaddies;

import javax.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* Class contains all the startup logic for the application.
*
* @param args A {@code String[]} of any potential runtime arguments
*/
@SpringBootApplication
public class App implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // Left blank, needed as part of an abstract interface/method.
  }

  /**
    * This contains all the overheading teardown logic.
    */
  @PreDestroy
  public void onTermination() {
    System.out.println("Je suis fini!");
  }
}
