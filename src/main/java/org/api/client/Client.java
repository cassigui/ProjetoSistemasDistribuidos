package org.api.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.entities.Message;
import org.api.utils.Menu;
import org.api.utils.MenuLogged;
import org.api.utils.StatusResponse;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class Client {
    public static void main(String[] args) throws IOException, SQLException {

        String nome = "";
        String ra = "";
        Integer id = 0;
        String token = "";
        String senha = "";
        String operacao = "";

        System.out.print("Digite o IP do servidor:");
        BufferedReader bfIP = new BufferedReader(new InputStreamReader(System.in));
        String ip = bfIP.readLine();

        System.out.print("Digite a porta do servidor:");
        BufferedReader bfPort = new BufferedReader(new InputStreamReader(System.in));
        int port = Integer.parseInt(bfPort.readLine());

        if (args.length > 0)
            ip = args[0];
        System.out.println("Tentando conectar ao IP " +
                ip + " na porta " + port);

        Socket serverSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            serverSocket = new Socket(ip, port);
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    serverSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Não foi possível reconhecer o ip: " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("\n" +
                    "Não foi possível obter o I/O para"
                    + " a conexão com o ip: " + ip);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;

        Menu.menu();

        while (true) {

            System.out.print("Mensagem: ");
            System.out.flush();
            userInput = stdIn.readLine();

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }

            if (userInput.equals("1")) {

                System.out.println("\n|- Sistema de login -|");

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    System.out.print("Digite seu RA: ");
                    ra = reader.readLine();

                    System.out.print("Digite a sua senha: ");
                    senha = reader.readLine();

                    operacao = "login";

                    String userJSON = String.format(
                            "{\"operacao\":\"%s\",\"ra\":\"%s\",\"senha\":\"%s\"}",
                            operacao, ra, senha
                    );

                    out.println(userJSON);
                    out.flush();

                    String serverResponse = in.readLine();

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    Message message = mapper.readValue(serverResponse, Message.class);

                    Integer status = message.getStatus();

                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                        if (status.equals(200)) {
                            MenuLogged.menu(ra);
                        }
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.out.println("Erro ao processar a entrada do usuário: " + e.getMessage());
                }

            }

            if (userInput.equals("2")) {
                System.out.println("\n|- Sistema de Cadastro -|");
                try {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    System.out.print("Digite o seu nome: ");
                    nome = reader.readLine();

                    System.out.print("Digite seu RA: ");
                    ra = reader.readLine();

                    System.out.print("Digite a sua senha: ");
                    senha = reader.readLine();

                    operacao = "cadastrarUsuario";
                    String userJSON = String.format(
                            "{\"operacao\":\"%s\",\"nome\":\"%s\",\"ra\":\"%s\",\"senha\":\"%s\"}",
                            operacao, nome, ra, senha
                    );

                    out.println(userJSON);
                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.err.println("Erro de entrada/saída: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                }

                Menu.menu();
            }
            if (userInput.equals("4")) {
                System.out.println("\n|- Sistema de Atualização -|");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    operacao = "editarUsuario";

                    token = ra;

                    System.out.print("Digite o RA do Usuário: ");
                    ra = reader.readLine();

                    System.out.print("Digite a nova senha: ");
                    senha = reader.readLine();

                    System.out.print("Digite o novo nome do Usuário: ");
                    nome = reader.readLine();

                    String usuario = String.format(
                            "{\"ra\":\"%s\",\"senha\":\"%s\",\"nome\":\"%s\"}",
                            ra, senha, nome
                    );

                    String userJSON = String.format(
                            "{\"operacao\":\"%s\",\"token\":\"%s\",\"usuario\":%s}",
                            operacao, token, usuario
                    );

                    out.println(userJSON);
                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.err.println("Erro de entrada/saída: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                }

            }

            if (userInput.equals("5")) {
                System.out.println("\n|- Sistema de Exclusão de Usuário-|");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    operacao = "excluirUsuario";

                    token = ra;
                    System.out.print("Digite o RA do Usuário: ");
                    ra = reader.readLine();

                    String userJSON = String.format(
                            "{\"operacao\":\"%s\",\"token\":\"%s\",\"ra\":\"%s\"}",
                            operacao, token, ra
                    );

                    out.println(userJSON);
                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.err.println("Erro de entrada/saída: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                }

            }

            if (userInput.equals("6")) {
                System.out.println("\n|- Listar Usuarios -|");
                try {
                    operacao = "listarUsuarios";

                    token = ra;

                    String userJSON = String.format(
                            "{\"operacao\":\"%s\",\"token\":\"%s\"}",
                            operacao, token
                    );

                    out.println(userJSON);
                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.err.println("Erro de entrada/saída: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                }

            }

            if (userInput.equals("7")) {
                System.out.println("\n|-Exibir dados do Usuario -|");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    operacao = "localizarUsuario";
                    token = ra;

                    System.out.print("Digite o RA do Usuário: ");
                    ra = reader.readLine();

                    String userJSON = String.format(
                            "{\"operacao\":\"%s\",\"token\":\"%s\",\"ra\":\"%s\"}",
                            operacao, token, ra
                    );

                    out.println(userJSON);
                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.err.println("Erro de entrada/saída: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                }

            }

            if (userInput.equals("8")) {
                System.out.println("\n|- Cadastrar Categoria -|");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    operacao = "salvarCategoria";
                    token = ra;

                    System.out.print("Digite o ID da categoria: ");
                    id = Integer.valueOf(reader.readLine());

                    System.out.print("Digite o nome da categoria: ");
                    nome = reader.readLine();

                    String categoryJSON = String.format(
                            "{\"operacao\":\"%s\",\"token\":\"%s\",\"id\":\"%d\",\"nome\":\"%s\"}",
                            operacao, token, id, nome
                    );

                    out.println(categoryJSON);
                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.err.println("Erro de entrada/saída: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                }

            }
            if (userInput.equals("9")) {
                System.out.println("\n|- Sistema de Exclusão de Categoria -|");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    operacao = "excluirCategoria";
                    token = ra;
                    System.out.print("Digite o ID da categoria: ");
                    id = Integer.valueOf(reader.readLine());

                    String userJSON = String.format(
                            "{\"operacao\":\"%s\",\"token\":\"%s\",\"id\":\"%d\"}",
                            operacao, token, id
                    );

                    out.println(userJSON);
                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.err.println("Erro de entrada/saída: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                }
            }
            if (userInput.equals("10")) {
                System.out.println("\n|- Sistema de Listagem de Categoria -|");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    operacao = "listarCategorias";
                    token = ra;

                    String userJSON = String.format(
                            "{\"operacao\":\"%s\",\"token\":\"%s\"}",
                            operacao, token
                    );

                    out.println(userJSON);
                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
                    } else {
                        System.out.println("Nenhuma resposta recebida do servidor.");
                    }

                } catch (IOException e) {
                    System.err.println("Erro de entrada/saída: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                }

            }

            if (userInput.equals("3")) {

                operacao = "logout";
                String userJSON = String.format(
                        "{\"operacao\":\"%s\",\"ra\":\"%s\"}",
                        operacao, ra
                );

                out.println(userJSON);

                break;
            }
        }
        try {

            bfPort.close();
            bfIP.close();
            out.close();
            in.close();
            stdIn.close();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Erro ao realizar o logout: " + e.getMessage());
        }
    }
}
