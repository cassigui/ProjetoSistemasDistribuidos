package org.api.daos;

import org.api.entities.Category;
import org.api.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private Connection connection;

    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    public String saveCategory(Category category) throws SQLException {
        String response = "";

        // Verifica se o nome já existe
        Category categoryExist = getCategoryByName(category.getNome());

        if (category.getId() == 0) {
            // Caso seja uma inserção (ID == 0)
            if (categoryExist != null) {
                response = "categoryExist"; // Nome já existe no banco
            } else {
                String sql = "INSERT INTO categorias(nome) VALUES (?)";

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, category.getNome());
                    int rowsAffected = stmt.executeUpdate();

                    response = (rowsAffected > 0) ? "success" : "error";
                } catch (SQLException e) {
                    System.out.println("Erro ao inserir categoria no banco de dados: " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
        } else {
            // Caso seja uma atualização (ID != 0)
            Category idExist = getCategoryById(category.getId());
            if (idExist == null) {
                response = "categoryNotExist"; // ID não encontrado no banco
            } else {
                if (categoryExist != null && categoryExist.getId() != category.getId()) {
                    response = "categoryExist";
                } else {
                    // Atualiza a categoria
                    if (updateCategory(category)) {
                        response = "successUpdate";
                    } else {
                        response = "error";
                    }
                }
            }
        }

        return response;
    }


    public Category getCategoryById(int id) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        Category categoryExist = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoryExist = new Category();
                    categoryExist.setNome(rs.getString("nome"));
                    categoryExist.setId(rs.getInt("id"));
                }
            }
        }

        return categoryExist;
    }

    public boolean deleteCategory (int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category getCategoryByName(String nome) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE nome = ?";
        Category idExist = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idExist = new Category();
                    idExist.setNome(rs.getString("nome"));
                    idExist.setId(rs.getInt("id"));
                }
            }
        }

        return idExist;
    }

    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE categorias SET nome = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getNome());
            stmt.setInt(2, category.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<Category> getCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setNome(rs.getString("nome"));
                categories.add(category);
            }
        }
        return categories;
    }

}
