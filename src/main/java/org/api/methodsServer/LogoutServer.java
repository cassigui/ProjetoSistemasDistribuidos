package org.api.methodsServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.User;
import org.api.utils.Menu;
import org.api.utils.MenuLogged;
import org.api.utils.StatusResponse;

import java.sql.Connection;
import java.sql.SQLException;

public class LogoutServer {
    public static void logout(String raJSON) throws SQLException {
        User user = null;
        Connection connection;
        UserDAO userDAO = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(raJSON, User.class);
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao processar o JSON: " + e.getMessage());
//            StatusResponse.status401("jsonconvert");
        }

        try {
            connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
//            StatusResponse.status401("conectionbd");
        }

            System.out.println(user.getRa()+ ", saiu");
            System.out.println("\nUsuário desconectado.\n");
            Menu.menu();
    }
}
