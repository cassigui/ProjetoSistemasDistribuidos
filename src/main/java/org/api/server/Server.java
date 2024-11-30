package org.api.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.database.DatabaseConnection;
import org.api.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Server extends Thread {
    protected static boolean activeServer = true;
    protected Socket clientSocket;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws SQLException, IOException {

        ServerSocket serverSocket = null;

        System.out.print("Digite a porta do Servidor:");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int port = Integer.parseInt(bf.readLine());

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor Iniciado.");
            System.out.println("Aguardando conexão...");
            try {
                while (activeServer) {
                    new Server(serverSocket.accept());
                    System.out.println("Usuário Conectado.");
                }
            } catch (IOException io) {
                System.out.println("Conexão recusada!");
                System.exit(1);
            }
        } catch (IOException io) {
            System.out.println("Não foi possível iniciar na porta " + port);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException io) {
                System.out.println("Não foi possível fechar a conexão com a porta " + port);
                System.exit(1);
            }
        }
    }

    private Server(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader bf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = bf.readLine()) != null) {
                System.out.println("Server: " + inputLine);
                out.println(inputLine);
            }

            System.out.println("Usuário desconectou.");
            out.close();
            bf.close();
            clientSocket.close();

        } catch (IOException io) {
            // Mensagem para desconexões inesperadas
            System.out.println("Conexão com o cliente perdida.");
        } finally {
            // Fechamento seguro do socket
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
                System.out.println("Cliente desconectado. Aguardando nova conexão...");
            } catch (IOException e) {
                System.out.println("Erro ao fechar o socket: " + e.getMessage());
            }
        }
    }
}
