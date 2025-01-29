package org.api.methodsServer.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateServer {
    public static String update(String userJSON) throws JsonProcessingException, SQLException {
        UserDAO userDAO;
        User user;
        String response;

        try {
            Connection connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);
        } catch (SQLException e) {
            System.out.println("Server: Erro ao conectar ao banco de dados: " + e.getMessage());
            return "connectionbd";
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(userJSON, User.class);
        } catch (JsonProcessingException e) {
            System.out.println("Server: Erro ao processar o JSON: " + e.getMessage());
            return "json";
        }

        if (userDAO.updateUser(user)) {
            response = "success";
        } else {
            response = "usernotfound";
        }

        return response;

    }
}
