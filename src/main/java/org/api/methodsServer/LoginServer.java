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

    public static boolean login(String userJSON) throws SQLException {
        User user;
        boolean logged = false;
        Connection connection;
        UserDAO userDAO;
        User loggedUser;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(userJSON, User.class);
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao processar o JSON: " + e.getMessage());
            StatusResponse.status401("jsonconvert");
            return false;
        }

        try {
            connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            StatusResponse.status401("conectionbd");
            return false;
        }

        loggedUser = userDAO.getUserLogin(user.getRa(), user.getPassword());

        if (loggedUser != null) {
//            userDAO.logged(loggedUser.getRa(), true);
            MenuLogged.menuLogged(loggedUser.getName());
        } else {
            StatusResponse.status401("credential");
        }

        return logged;
    }
}
