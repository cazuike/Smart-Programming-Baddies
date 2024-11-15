package com.smartprogrammingbaddies;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import javax.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* Class contains all the startup logic for the application.
*/
@SpringBootApplication
public class App implements CommandLineRunner {
  /**
  * Main method to run the application. It loads the environment variables from the .env file.
  *
  * @param args A {@code String[]} of any potential runtime arguments
  */
  public static void main(String[] args) {
    // Loads the environment variables
    Dotenv dotenv = Dotenv.configure().load();
    for (DotenvEntry entry : dotenv.entries()) {
      System.setProperty(entry.getKey(), entry.getValue());
    }

    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
  }

  @PreDestroy
  public void onTermination() {
    System.out.println("Terminated the application");
  }
}
