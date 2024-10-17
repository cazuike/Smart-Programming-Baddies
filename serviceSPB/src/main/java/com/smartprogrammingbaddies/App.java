package com.smartprogrammingbaddies;

import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* Class contains all the startup logic for the application.
*
* @param args A {@code String[]} of any potential runtime arguments
*/
@SpringBootApplication
public class App  implements CommandLineRunner {
  @Autowired
  private SetupDatabase setupDatabase;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  /**
  * This function is required for the App to run.
  *
  * @param args A {@code String[]} of any potential runtime arguments
  */
  @Override
  public void run(String... args) throws Exception {
    return;
  }

  /**
    * This contains all the overheading teardown logic.
    */
  @PreDestroy
  public void onTermination() {
    System.out.println("Je suis fini!");
  }
}
