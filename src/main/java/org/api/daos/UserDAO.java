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

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (nome, ra, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getRa());
            stmt.setString(3, user.getSenha());
            stmt.executeUpdate();
        }
    }

//    public Boolean isLogged(String ra) throws SQLException {
//        String sql = "SELECT logged FROM users WHERE ra = ?";
//        Boolean logged = null;
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, ra);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    logged = rs.getBoolean("logged");
//                }
//            }
//        }
//
//        return logged;
//    }

//    public void logged(String ra, Boolean logged) throws SQLException {
//        String sql = "UPDATE users SET logged = ? WHERE ra = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setBoolean(1, logged);
//            stmt.setString(2, ra);
//            stmt.executeUpdate();
//        }
//    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<User>();
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

    public User getUserLogin(String ra, String senha) throws SQLException {
        String sql = "SELECT * FROM users WHERE ra = ? AND senha = ?";
        User user = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ra);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setNome(rs.getString("nome"));
                    user.setRa(rs.getString("ra"));
                    user.setSenha(rs.getString("senha"));
                }
            }
        }

        return user;
    }

}
