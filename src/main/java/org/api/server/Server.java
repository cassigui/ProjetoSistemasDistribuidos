package org.api.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.entities.Message;
import org.api.methodsClients.LoginClient;
import org.api.methodsClients.RegisterClient;
import org.api.utils.StatusResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
            String response = "";

            while ((inputLine = bf.readLine()) != null) {
                System.out.println("Server: " + inputLine);

                try {
                    Message message = objectMapper.readValue(inputLine, Message.class);

                    String operation = message.getOperation();
                    String name = message.getName();
                    String ra = message.getRa();
                    String password = message.getPassword();

                    if (operation.equalsIgnoreCase("login")) {
                        if (LoginClient.login(ra, password).equalsIgnoreCase("success")) {
                            out.println(StatusResponse.status200(operation, ra));
                        } else if (LoginClient.login(ra, password).equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operation, "connectionbd"));
                        }else if (LoginClient.login(ra, password).equalsIgnoreCase("jsonread")) {
                            out.println(StatusResponse.status401(operation, "jsonread"));
                        } else if (LoginClient.login(ra, password).equalsIgnoreCase("invalid")) {
                            out.println(StatusResponse.status401(operation, "invalid"));
                        } else if (LoginClient.login(ra, password).equalsIgnoreCase("credential")) {
                            out.println(StatusResponse.status401(operation, "credential"));
                        }
                    } else if (operation.equalsIgnoreCase("Register")) {
                        if (RegisterClient.register(name, ra, password)) {
                            out.println(StatusResponse.status200(operation, ra));
                        } else {
                            out.println(StatusResponse.status401(operation, "credential"));
                        }
                    } else {
                        out.println(StatusResponse.status401(operation, "invalid"));
                    }

                } catch (JsonProcessingException | SQLException e) {
                    System.out.println("Server" + "Erro ao processar JSON recebido: " + e.getMessage());

                    response = String.format(
                            "{\"operation\":\"%s\",\"status\":\"%s\",\"ra\":\"%s\"}",
                            "401", "Não foi possível ler o JSON recebido."
                    );
                    out.println(response);
                }
            }
            System.out.println("Usuário desconectou.");
            out.close();
            bf.close();
            clientSocket.close();

        } catch (IOException io) {
            System.out.println("Conexão com o cliente perdida.");
        } finally {
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
