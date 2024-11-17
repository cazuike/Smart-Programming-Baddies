package com.smartprogrammingbaddies;

import javax.sql.DataSource;

import com.smartprogrammingbaddies.utils.SqlConnection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DBConfig {

    @Bean
    @Profile("default")
    public DataSource mysqlDataSource() {
        return SqlConnection.createSqlConnection();
    }

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
