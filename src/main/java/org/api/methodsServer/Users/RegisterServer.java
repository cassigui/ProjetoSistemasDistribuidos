package org.api.methodsServer.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class RegisterServer {
    public static String register(String userJSON) throws JsonProcessingException, SQLException, InputMismatchException, NumberFormatException {
        UserDAO userDAO = null;
        User user;
        String response = "";

        try {
            Connection connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);

        } catch (SQLException e) {
            System.out.println("Server: " + "Erro ao conectar ao banco de dados: " + e.getMessage());
            return "conectionbd";
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(userJSON, User.class);
        } catch (JsonProcessingException e) {
            System.out.println("Server: " + "Erro ao processar o JSON: " + e.getMessage());
            return "json";
        }


        if (userDAO.saveUser(user)) {
            return "success";
        } else {
            return "userexist";
        }

    }
}
