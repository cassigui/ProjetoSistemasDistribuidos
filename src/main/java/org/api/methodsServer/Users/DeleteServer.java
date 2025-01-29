package org.api.methodsServer.Users;

import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DeleteServer {
    public static String delete(String ra) throws SQLException {
        UserDAO userDAO;
        String response;

        try {
            Connection connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);
        } catch (SQLException e) {
            System.out.println("Server: Erro ao conectar ao banco de dados: " + e.getMessage());
            return "connectionbd";
        }

        if (userDAO.deleteUser(ra)) {
            response = "success";
        } else {
            response = "usernotfound";
        }

        return response;
    }
}
