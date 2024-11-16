package com.smartprogrammingbaddies;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Configuration
public class SqlConnection {
  private static final Dotenv dotenv = Dotenv.load();

    private static final String INSTANCE_CONNECTION_NAME =
          dotenv.get("INSTANCE_CONNECTION_NAME");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASS = dotenv.get("DB_PASS");
    private static final String DB_NAME = dotenv.get("DB_NAME");

  @Autowired
  private com.smartprogrammingbaddies.ApiKeyRepository apiKeyRepository;


  public static DataSource createSqlConnection() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
    config.setUsername(DB_USER);
    config.setPassword(DB_PASS);
    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);
    config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
    System.err.println(config.getJdbcUrl());
    System.out.println(config.getJdbcUrl());

    return new HikariDataSource(config);
  }

  public boolean enrollClient(String apiKey) {
    try {
      if (!apiKeyRepository.existsByApiKey(apiKey)) {
        apiKeyRepository.save(new com.smartprogrammingbaddies.ApiKey(apiKey));
        System.out.println("API key added successfully!");
      } else {
        System.out.println("API key already exists!");
      }
      return true;
    } catch (Exception e) {
      System.err.println("Failed to add API key.");
      e.printStackTrace();
    }
    return false;
  }
  public boolean verifyClient(String apiKey) {
    try {
      boolean exists = apiKeyRepository.existsByApiKey(apiKey);
      if (exists) {
        System.out.println("API key exists in the database.");
      } else {
        System.out.println("API key does not exist.");
      }
      return exists;
    } catch (Exception e) {
      System.err.println("Failed to verify API key.");
      e.printStackTrace();
      return false;
    }
  }

  public boolean removeClient(String apiKey) {
    try {
      Optional<com.smartprogrammingbaddies.ApiKey> apiKeyEntry = apiKeyRepository.findByApiKey(apiKey);
      if (apiKeyEntry.isPresent()) {
        apiKeyRepository.delete(apiKeyEntry.get());
        System.out.println("API key deleted successfully!");
        return true;
      } else {
        System.out.println("API key not found.");
        return false;
      }
    } catch (Exception e) {
      System.err.println("Failed to delete API key.");
      e.printStackTrace();
      return false;
    }
  }
}
