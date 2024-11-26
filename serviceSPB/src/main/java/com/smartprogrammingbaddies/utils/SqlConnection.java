package com.smartprogrammingbaddies.utils;

import com.smartprogrammingbaddies.auth.ApiKey;
import com.smartprogrammingbaddies.auth.ApiKeyRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contains the SqlConnection class.
 */
public class SqlConnection {
  private static final Dotenv dotenv = Dotenv.load();

  private static final String INSTANCE_CONNECTION_NAME =
          dotenv.get("INSTANCE_CONNECTION_NAME");
  private static final String DB_USER = dotenv.get("DB_USER");
  private static final String DB_PASS = dotenv.get("DB_PASS");
  private static final String DB_NAME = dotenv.get("DB_NAME");

  @Autowired
  private ApiKeyRepository apiKeyRepository;

  /**
   * Creates a connection to the SQL database.
   *
   * @return A {@code DataSource} object representing the SQL database connection.
   */
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

  /**
   * Enrolls a client into the database.
   *
   * @param apiKey A {@code String} representing the client's API key.
   * @return A {@code boolean} indicating if the client was successfully enrolled.
   */
  public boolean enrollClient(String apiKey) {
    try {
      if (!apiKeyRepository.existsByApiKey(apiKey)) {
        apiKeyRepository.save(new ApiKey(apiKey));
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

  /**
   * Verifies a client's API key is valid.
   *
   * @param apiKey A {@code String} representing the client's API key.
   * @return A {@code boolean} indicating if the client's API key is valid.
   */
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

  /**
   * Removes a client's API key from the database.
   *
   * @param apiKey A {@code String} representing the client's API key.
   * @return A {@code boolean} indicating if the client's API key was successfully removed.
   */
  public boolean removeClient(String apiKey) {
    try {
      Optional<ApiKey> apiKeyEntry = apiKeyRepository.findByApiKey(apiKey);
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
