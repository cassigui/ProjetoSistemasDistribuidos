package org.api.client;

import org.api.entities.User;
import org.api.methodsClients.LoginClient;
import org.api.methodsClients.LogoutClient;
import org.api.methodsClients.Register;
import org.api.utils.Menu;
import org.api.utils.StatusResponse;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class Client {
    public static void main(String[] args) throws IOException, SQLException {

        String name = "";
        String ra = "";
        String password = "";

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

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(ip, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
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
                    password = reader.readLine();

                    LoginClient.login(ra, password);

                } catch (IOException e) {
                    System.out.println("Erro ao processar a entrada do usuário: " + e.getMessage());
                }
            }


//            if (userInput.equals("2")) {
//
//                out.println(" O usuário esta fazendo o cadastro...");
//                User user = new User();
//
//                System.out.println("\n|- Sistema de Cadastro -|");
//                try {
//                    System.out.print("Digite o seu nome:");
//                    BufferedReader bfName = new BufferedReader(new InputStreamReader(System.in));
//                    name = bfName.readLine();
//                    user.setNome(name);
//
//                    System.out.print("Digite seu RA:");
//                    BufferedReader bfUser = new BufferedReader(new InputStreamReader(System.in));
//                    ra = bfUser.readLine();
//                    user.setRa(ra);
//
//                    System.out.print("Digite a sua senha:");
//                    BufferedReader bfPassword = new BufferedReader(new InputStreamReader(System.in));
//                    password = bfPassword.readLine();
//                    user.setSenha(password);
//
//                } catch (InputMismatchException | NumberFormatException e) {
//                    System.out.print("\nCliente:");
//                    StatusResponse.status401("invalid");
//                }
//
//                if (Register.register(user)) {
//                    out.println("Usuário:" + user.getRa() + ", Cadastrado com Sucesso!");
//                } else {
//                    StatusResponse.status401("invalid");
//                    out.println("Não foi possível cadastrar o usuário");
//                }
//                Menu.menu();
//            }

            if (userInput.equals("3")) {
                LogoutClient.logout(ra);
            }
        }

        bfPort.close();
        bfIP.close();
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
