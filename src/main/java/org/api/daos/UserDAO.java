package org.api.daos;

import org.api.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean saveUser(User user) throws SQLException {
        User userExist = getUserLogin(user.getRa());
        if (userExist != null) {
            System.out.println("Usuário encontrado: " + userExist);
            return false;
        } else {
            String sql = "INSERT INTO users (nome, ra, senha) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getNome());
                stmt.setString(2, user.getRa());
                stmt.setString(3, user.getSenha());
                stmt.executeUpdate();
                System.out.println("Server: Usuário cadastrado : " + user);
            }
            return true;
        }
    }

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET nome = ?, senha = ? WHERE ra = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getSenha());
            stmt.setString(3, user.getRa());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setRa(rs.getString("ra"));
                user.setNome(rs.getString("nome"));
                user.setSenha(rs.getString("senha"));
                users.add(user);
            }
        }
        return users;
    }

    public User getUserLogin(String ra) throws SQLException {
        String sql = "SELECT * FROM users WHERE ra = ?";
        User userExist = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ra);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userExist = new User();
                    userExist.setNome(rs.getString("nome"));
                    userExist.setRa(rs.getString("ra"));
                    userExist.setSenha(rs.getString("senha"));
                }
            }
        }

        return userExist;
    }

    public boolean deleteUser(String ra) throws SQLException {
        String sql = "DELETE FROM users WHERE ra = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ra);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
