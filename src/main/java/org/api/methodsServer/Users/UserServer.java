package org.api.methodsServer.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServer {
    public static String findUser(String ra) throws JsonProcessingException, SQLException {
        UserDAO userDAO;
        String responseJson;

        try (Connection connection = DatabaseConnection.getConnection()) {
            userDAO = new UserDAO(connection);

            User response = userDAO.getUserLogin(ra);

            if (response != null) {
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
