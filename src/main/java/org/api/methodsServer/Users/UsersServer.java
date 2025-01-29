package org.api.methodsServer.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsersServer {
    public static String listUsers() throws JsonProcessingException, SQLException {
        UserDAO userDAO;
        String responseJson;

        try (Connection connection = DatabaseConnection.getConnection()) {
            userDAO = new UserDAO(connection);

            List<User> response = userDAO.getUsers();

            if (response != null && !response.isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                responseJson = objectMapper.writeValueAsString(response);
            } else {
                responseJson = "noData";
            }
        } catch (SQLException e) {
            System.err.println("Server: Erro ao conectar ao banco de dados: " + e.getMessage());
            return "{\"error\":\"Erro ao conectar ao banco de dados.\"}";
        } catch (JsonProcessingException e) {
            System.err.println("Server: Erro ao processar JSON: " + e.getMessage());
            return "{\"error\":\"Erro ao processar os dados.\"}";
        }

        return responseJson;
    }
}
