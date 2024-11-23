package com.smartprogrammingbaddies.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;

/**
 * This class contains the SqlConnection class.
 */
@Configuration
public abstract class SqlConnection {
  private static final Dotenv dotenv = Dotenv.load();

  private static final String INSTANCE_CONNECTION_NAME =
          dotenv.get("INSTANCE_CONNECTION_NAME");
  private static final String DB_USER = dotenv.get("DB_USER");
  private static final String DB_PASS = dotenv.get("DB_PASS");
  private static final String DB_NAME = dotenv.get("DB_NAME");

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
}
