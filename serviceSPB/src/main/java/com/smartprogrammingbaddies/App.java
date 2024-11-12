package com.smartprogrammingbaddies;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import javax.sql.DataSource;
import javax.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

/**
* Class contains all the startup logic for the application.
*
* @param args A {@code String[]} of any potential runtime arguments
*/
@SpringBootApplication
public class App implements CommandLineRunner {

  Connection connection;

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
    DataSource dataSource = SqlConnection.createSqlConnection();
    connection = dataSource.getConnection();
    ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Event");
    while (resultSet.next()) {
      System.out.println(resultSet.getString("name"));
    }
  }

  /**
    * This contains all the overheading teardown logic.
    */
  @PreDestroy
  public void onTermination() {
    System.out.println("Je suis fini!");
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
