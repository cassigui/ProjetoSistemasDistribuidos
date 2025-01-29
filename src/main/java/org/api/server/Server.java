package org.api.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.entities.Message;
import org.api.methodsClients.Categories.CreateClientCategories;
import org.api.methodsClients.Categories.DeleteClientCategories;
import org.api.methodsClients.Categories.ListClientCategories;
import org.api.methodsClients.Users.*;
import org.api.utils.LoggedUsers;
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
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    Message message = mapper.readValue(inputLine, Message.class);

                    String operacao = message.getOperacao();
                    String token = message.getToken();
                    String nome = message.getNome();
                    String ra = message.getRa();
                    String senha = message.getSenha();
                    int id = message.getId();

                    if (operacao.equalsIgnoreCase("login")) {

                        String userJSON = String.format(
                                "{usuario\":{\"ra\":\"%s\",\"senha\":\"%s\",\"nome\":\"%s\"}}",
                                ra, senha, nome);
                        String loginResult = LoginClient.login(ra, senha); // Armazena o resultado da chamada

                        if (loginResult.equalsIgnoreCase("success")) {
                            out.println(StatusResponse.status200(operacao, ra));
                            LoggedUsers.addUser(ra);
                        } else if (loginResult.equalsIgnoreCase("isLogged")) {
                            out.println(StatusResponse.status401(operacao, "isLogged"));
                        } else if (loginResult.equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operacao, "connectionbd"));
                        } else if (loginResult.equalsIgnoreCase("jsonread")) {
                            out.println(StatusResponse.status401(operacao, "jsonread"));
                        } else if (loginResult.equalsIgnoreCase("invalid")) {
                            out.println(StatusResponse.status401(operacao, "invalid"));
                        } else if (loginResult.equalsIgnoreCase("credential")) {
                            out.println(StatusResponse.status401(operacao, "credential"));
                        }
                    } else if (operacao.equalsIgnoreCase("cadastrarUsuario")) {

                        String registerResult = RegisterClient.register(nome, ra, senha); // Armazena o resultado da chamada

                        if (registerResult.equalsIgnoreCase("success")) {
                            out.println(StatusResponse.status201(operacao));
                        } else if (registerResult.equalsIgnoreCase("userexist")) {
                            out.println(StatusResponse.status401(operacao, "userexist"));
                        } else if (registerResult.equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operacao, "connectionbd"));
                        } else if (registerResult.equalsIgnoreCase("jsonread")) {
                            out.println(StatusResponse.status401(operacao, "jsonread"));
                        } else if (registerResult.equalsIgnoreCase("invalid")) {
                            out.println(StatusResponse.status401(operacao, "invalid"));
                        } else if (registerResult.equalsIgnoreCase("credential")) {
                            out.println(StatusResponse.status401(operacao, "credential"));
                        }
                    } else if (operacao.equalsIgnoreCase("editarUsuario")) {
                        String nomeUser = message.getUsuario().getNome();
                        String senhaUser = message.getUsuario().getSenha();
                        String raUser = message.getUsuario().getRa();

                        String updateResult = UpdateClient.update(token, nomeUser, raUser, senhaUser);

                        if (updateResult.equalsIgnoreCase("success")) {
                            out.println(StatusResponse.status201Update(operacao));
                        } else if (updateResult.equalsIgnoreCase("usernotfound")) {
                            out.println(StatusResponse.status401(operacao, "usernotfound"));
                        } else if (updateResult.equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operacao, "connectionbd"));
                        } else if (updateResult.equalsIgnoreCase("jsonread")) {
                            out.println(StatusResponse.status401(operacao, "jsonread"));
                        } else if (updateResult.equalsIgnoreCase("invalid")) {
                            out.println(StatusResponse.status401(operacao, "invalid"));
                        } else if (updateResult.equalsIgnoreCase("credential")) {
                            out.println(StatusResponse.status401(operacao, "credential"));
                        } else if (updateResult.equalsIgnoreCase("unauthorized")) {
                            out.println(StatusResponse.unauthorized(operacao));
                        }
                    } else if (operacao.equalsIgnoreCase("excluirUsuario")) {
                        String deleteResult = DeleteClient.excluir(token, ra);

                        if (deleteResult.equalsIgnoreCase("success")) {
                            out.println(StatusResponse.status201Delete(operacao));
                        } else if (deleteResult.equalsIgnoreCase("usernotfound")) {
                            out.println(StatusResponse.status401(operacao, "usernotfound"));
                        } else if (deleteResult.equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operacao, "connectionbd"));
                        } else if (deleteResult.equalsIgnoreCase("jsonread")) {
                            out.println(StatusResponse.status401(operacao, "jsonread"));
                        } else if (deleteResult.equalsIgnoreCase("invalid")) {
                            out.println(StatusResponse.status401(operacao, "invalid"));
                        } else if (deleteResult.equalsIgnoreCase("credential")) {
                            out.println(StatusResponse.status401(operacao, "credential"));
                        } else if (deleteResult.equalsIgnoreCase("unauthorized")) {
                            out.println(StatusResponse.unauthorized(operacao));
                        }

                    } else if (operacao.equalsIgnoreCase("listarUsuarios")) {
                        String usersList = UsersClient.listar(token);

                        if (usersList.equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operacao, "connectionbd"));
                        } else if (usersList.equalsIgnoreCase("unauthorized")) {
                            out.println(StatusResponse.unauthorized(operacao));
                        } else if (usersList.equalsIgnoreCase("nodata")) {
                            out.println(StatusResponse.noData(operacao));
                        } else {
                            out.println(String.format(
                                    "{\"status\":201,\"operacao\":\"%s\",\"usuarios\":%s}",
                                    operacao,
                                    usersList
                            ));
                        }
                    } else if (operacao.equalsIgnoreCase("localizarUsuario")) {
                        String user = UserClient.localizar(token, ra);

                        if (user.equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operacao, "connectionbd"));
                        } else if (user.equalsIgnoreCase("unauthorized")) {
                            out.println(StatusResponse.unauthorized(operacao));
                        } else {
                            out.println(String.format(
                                    "{\"status\":201,\"operacao\":\"%s\",\"usuario\":\"%s\"}",
                                    operacao,
                                    user
                            ));
                        }
                    } else if (operacao.equalsIgnoreCase("salvarCategoria")) {
                        String categoryResult = CreateClientCategories.createCategory(token, id, nome);

                        if (categoryResult.equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operacao, "connectionbd"));
                        } else if (categoryResult.equalsIgnoreCase("unauthorized")) {
                            out.println(StatusResponse.unauthorized(operacao));
                        } else if (categoryResult.equalsIgnoreCase("jsonread")) {
                            out.println(StatusResponse.status401(operacao, "jsonread"));
                        } else if (categoryResult.equalsIgnoreCase("success")) {
                            out.println(StatusResponse.status201CategoryCreate(operacao));
                        } else if (categoryResult.equalsIgnoreCase("successUpdate")) {
                            out.println(StatusResponse.status201CategoryUpdate(operacao));
                        } else if (categoryResult.equalsIgnoreCase("error")) {
                            out.println(StatusResponse.internalError(operacao));
                        } else if (categoryResult.equalsIgnoreCase("tableNotExist")) {
                            out.println(StatusResponse.tableNotExist(operacao));
                        } else if (categoryResult.equalsIgnoreCase("categoryExist")) {
                            out.println(StatusResponse.status401(operacao, "categoryExist"));
                        } else if (categoryResult.equalsIgnoreCase("categoryNotExist")) {
                            out.println(StatusResponse.status401(operacao, "categoryNotExist"));
                        } else if (categoryResult.equalsIgnoreCase("invalid")) {
                            out.println(StatusResponse.status401(operacao, "invalid"));
                        }
                    } else if (operacao.equalsIgnoreCase("listarCategorias")) {
                        String categoryList = ListClientCategories.listar(token);

                        if (categoryList.equalsIgnoreCase("connectionbd")) {
                            out.println(StatusResponse.status401(operacao, "connectionbd"));
                        } else if (categoryList.equalsIgnoreCase("unauthorized")) {
                            out.println(StatusResponse.unauthorized(operacao));
                        } else if (categoryList.equalsIgnoreCase("nodata")) {
                            out.println(StatusResponse.noData(operacao));
                        } else {
                            out.println(String.format(
                                    "{\"status\":201,\"operacao\":\"%s\",\"categorias\":%s}",
                                    operacao,
                                    categoryList
                            ));
                        }

                    } else if (operacao.equalsIgnoreCase("excluirCategoria")) {
                        String deleteResult = DeleteClientCategories.excluir(token, id);

                        if (deleteResult.equalsIgnoreCase("success")) {
                            out.println(StatusResponse.status201Delete(operacao));
                        }

                    } else if (operacao.equalsIgnoreCase("logout")) {
                        if (LoggedUsers.desconectUser(ra)) {
                            out.println(StatusResponse.status200Loggin());
                        }
                        out.close();
                        bf.close();
                        clientSocket.close();
                        break;
                    }

                } catch (JsonProcessingException | SQLException e) {
                    System.out.println("Server" + "Erro ao processar JSON recebido: " + e.getMessage());

                    response = String.format(
                            "{\"operacao\":\"%s\",\"status\":\"%s\",\"ra\":\"%s\"}",
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
