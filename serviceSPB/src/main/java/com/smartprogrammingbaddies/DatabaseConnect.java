package com.smartprogrammingbaddies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;

@Configuration
public class DatabaseConnect {
    private final DataSource dataSource;

    @Autowired
    public DatabaseConnect(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        System.out.println("Testing database connection on startup...");
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Database connection successful with URL: " + connection.getMetaData().getURL());
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    public void enrollClient(String apiKey) {
        String insertQuery = "INSERT INTO api_keys (api_key) VALUES (?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setString(1, apiKey);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("API key added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add API key.");
            e.printStackTrace();
        }
    }

    public boolean verifyClient(String apiKey) {
        String selectQuery = "SELECT COUNT(*) FROM api_keys WHERE api_key = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {

            statement.setString(1, apiKey);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("API key exists in the database.");
                return true;
            } else {
                System.out.println("API key does not exist.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Failed to verify API key.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeClient(String apiKey) {
        String deleteQuery = "DELETE FROM api_keys WHERE api_key = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setString(1, apiKey);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("API key deleted successfully!");
                return true;
            } else {
                System.out.println("API key not found.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete API key.");
            e.printStackTrace();
            return false;
        }
    }

}
