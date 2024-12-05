package org.api.methodsServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.User;
import org.api.utils.MenuLogged;
import org.api.utils.StatusResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoginServer {

    public static String login(String userJSON) throws SQLException {
        User user = null;
        Connection connection;
        UserDAO userDAO = null;
        User loggedUser;
        String response = "";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(userJSON, User.class);
        } catch (JsonProcessingException e) {
            System.out.println("Server: " + "Erro ao processar o JSON: " + e.getMessage());
            return "json";
        }

        try {
            connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);
        } catch (SQLException e) {
            System.out.println("Server: " + "Erro ao conectar ao banco de dados: " + e.getMessage());
            return "connectionbd";
        }

        loggedUser = userDAO != null ? userDAO.getUserLogin(
                user != null ? user.getRa() : null, user != null ? user.getSenha() : null) : null;

        if (loggedUser != null) {
            response = "success";
            System.out.println("Server: " + loggedUser.getNome() + " Logado");
        } else {
            System.out.println("Server: " + " credenciais inválidas.");
            response = "credencial";
        }

        return response;
    }
}
