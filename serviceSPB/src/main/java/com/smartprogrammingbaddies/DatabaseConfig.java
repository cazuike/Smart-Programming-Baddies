package com.smartprogrammingbaddies;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * This class contains the Database Configuration class.
 */
@Configuration
public class DatabaseConfig {
  /**
   * Creates a H2 in-memory database for testing purposes.
   *
   * @return A {@code DataSource} object representing the H2 in-memory database.
   */
  @Bean
  @Profile("test")
  public DataSource h2DataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
    config.setUsername("sa");
    config.setPassword("password");
    return new HikariDataSource(config);
  }
}
