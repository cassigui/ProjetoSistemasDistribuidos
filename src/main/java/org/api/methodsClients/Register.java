package org.api.methodsClients;

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

public class Register {
    public static boolean register(User user) throws JsonProcessingException, SQLException, InputMismatchException, NumberFormatException {
        UserDAO userDAO;

        try {
            Connection connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);

        } catch (SQLException e) {
            StatusResponse.status401("conectionbd");
            return false;
        }

        try {
            userDAO.saveUser(user);
            ObjectMapper objectMapper = new ObjectMapper();
            String userJSON = objectMapper.writeValueAsString(user);

            StatusResponse.status200();
            System.out.println("Usuário Cadastrado com sucesso!");
            System.out.println("Dados do usuário:" + userJSON + "\n");
            return true;
        } catch (JsonProcessingException e) {
            System.out.println("\nNão foi possível converter os dados para JSON" + "\n");
            return false;
        } catch (InputMismatchException | NumberFormatException e) {
            StatusResponse.status401("invalid");
            return false;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.print("Banco de dados: ");
            StatusResponse.status401("invalid");
            return false;
        }

    }
}
