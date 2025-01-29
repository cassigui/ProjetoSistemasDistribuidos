package org.api.methodsServer.Categories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.CategoryDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class CreateServerCategories {
    public static String createCategory(String categoryJSON) throws JsonProcessingException, SQLException, InputMismatchException, NumberFormatException {
        CategoryDAO categoryDAO = null;
        Category category;
        String response = "";

        try {
            Connection connection = DatabaseConnection.getConnection();
            categoryDAO = new CategoryDAO(connection);

        } catch (SQLException e) {
            System.out.println("Server: " + "Erro ao conectar ao banco de dados: " + e.getMessage());
            return "connectionbd";
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            category = objectMapper.readValue(categoryJSON, Category.class);
        } catch (JsonProcessingException e) {
            System.out.println("Server: " + "Erro ao processar o JSON: " + e.getMessage());
            return "jsonread";
        }

        if (categoryDAO.saveCategory(category).equalsIgnoreCase("success")) {
            response = "success";
        } else if (categoryDAO.saveCategory(category).equalsIgnoreCase("tableNotExist")) {
            response = "tableNotExist";
        } else if (categoryDAO.saveCategory(category).equalsIgnoreCase("categoryExist")) {
            response = "categoryExist";
        } else if (categoryDAO.saveCategory(category).equalsIgnoreCase("error")) {
            response = "error";
        } else if (categoryDAO.saveCategory(category).equalsIgnoreCase("categoryNotExist")) {
            response = "categoryNotExist";
        } else if (categoryDAO.saveCategory(category).equalsIgnoreCase("successUpdate")) {
            response = "successUpdate";
        }
        return response;
    }
}
