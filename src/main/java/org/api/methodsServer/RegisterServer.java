package org.api.methodsServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.User;
import org.api.utils.StatusResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.InputMismatchException;

public class RegisterServer {
    public static boolean register(String userJSON) throws JsonProcessingException, SQLException, InputMismatchException, NumberFormatException {
        UserDAO userDAO;
        User user;

        try {
            Connection connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);

        } catch (SQLException e) {
//            System.out.println(StatusResponse.status401("conectionbd"));
            return false;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(userJSON, User.class);
            userDAO.saveUser(user);
            return true;
        } catch (JsonProcessingException e) {
            System.out.println("\nNão foi possível converter o JSON para OBJECT" + "\n");
            return false;
        } catch (InputMismatchException | NumberFormatException e) {
//            System.out.println(StatusResponse.status401("invalid"));
            return false;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.print("Banco de dados: ");
//            System.out.println(StatusResponse.status401("invalid"));
            return false;
        }

    }
}
