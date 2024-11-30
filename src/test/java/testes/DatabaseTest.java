package testes;

import org.api.model.Message;

import org.api.database.DatabaseConnection;
import org.api.server.Server;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {

    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Conexão com o banco de dados bem-sucedida!");
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
        }

        Message testMessage = new Message("info", "Teste de mensagem no banco de dados");

    }
}
