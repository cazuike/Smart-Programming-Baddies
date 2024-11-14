package com.example.serviceSPB;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SqlConnection {
  private static final String INSTANCE_CONNECTION_NAME =
          "team-project-439523:us-east1:ase-project";
  private static final String DB_USER = "root";
  private static final String DB_PASS = "{^3=6|QC;'fn:uEm";
  private static final String DB_NAME = "service";

  public static DataSource createSqlConnection() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
    config.setUsername(DB_USER);
    config.setPassword(DB_PASS);
    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);
    config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
    return new HikariDataSource(config);
  }
}
