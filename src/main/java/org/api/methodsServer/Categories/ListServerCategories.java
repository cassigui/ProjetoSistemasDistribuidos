package org.api.methodsServer.Categories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.daos.CategoryDAO;
import org.api.daos.UserDAO;
import org.api.database.DatabaseConnection;
import org.api.entities.Category;
import org.api.entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListServerCategories {
    public static String listCategories() throws JsonProcessingException, SQLException {
        CategoryDAO categoryDAO;
        String responseJson;

        try (Connection connection = DatabaseConnection.getConnection()) {
            categoryDAO = new CategoryDAO(connection);

            List<Category> response = categoryDAO.getCategories();

            if (response != null && !response.isEmpty()) {
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

    public static String findCategory(int id) throws JsonProcessingException, SQLException {
        CategoryDAO categoryDAO;
        String responseJson;

        try (Connection connection = DatabaseConnection.getConnection()) {
            categoryDAO = new CategoryDAO(connection);

            Category response = categoryDAO.getCategoryById(id);

            if (response != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                responseJson = objectMapper.writeValueAsString(response);
            } else {
                responseJson = "categorynotfound";
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
