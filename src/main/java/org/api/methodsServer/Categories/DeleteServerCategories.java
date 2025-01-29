package org.api.methodsServer.Categories;

import org.api.daos.CategoryDAO;
import org.api.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DeleteServerCategories {
    public static String delete(int id) throws SQLException {
        CategoryDAO categoryDAO;
        String response;

        try {
            Connection connection = DatabaseConnection.getConnection();
            categoryDAO = new CategoryDAO(connection);
        } catch (SQLException e) {
            System.out.println("Server: Erro ao conectar ao banco de dados: " + e.getMessage());
            return "connectionbd";
        }

        if (categoryDAO.deleteCategory(id)) {
            response = "success";
        } else {
            response = "usernotfound";
        }

        return response;
    }
}
