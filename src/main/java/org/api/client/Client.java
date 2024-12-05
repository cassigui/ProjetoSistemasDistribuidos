package org.api.client;

import org.api.utils.Menu;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class Client {
    public static void main(String[] args) throws IOException, SQLException {

        String nome = "";
        String ra = "";
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
                    if (serverResponse != null) {
                        System.out.println("Resposta do servidor: " + serverResponse);
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

            if (userInput.equals("3")) {

                operacao = "logout";
                String userJSON = String.format(
                        "{\"operacao\":\"%s\",\"ra\":\"%s\"}",
                        operacao, ra
                );

                out.println(userJSON);

                bfIP.close();
                out.close();
                in.close();
                stdIn.close();
                serverSocket.close();
                bfPort.close();
            }
        }

        bfPort.close();
        bfIP.close();
        out.close();
        in.close();
        stdIn.close();
        serverSocket.close();
    }
}
