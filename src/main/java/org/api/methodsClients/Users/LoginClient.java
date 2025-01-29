package org.api.methodsClients.Users;

import netscape.javascript.JSException;
import org.api.methodsServer.Users.LoginServer;

import java.sql.SQLException;

public class LoginClient {

    public static String login(String ra, String senha) throws SQLException {
        String response = "";
        String userJSON = "";

        if ((ra.length() == 7 && ra.matches("\\d{7}")) && (senha.length() >= 8 && senha.length() <= 20 && senha.matches("[a-zA-Z]{8,20}"))) {
            try {
                // Correção: Envolver "ra" entre aspas
                userJSON = String.format("{\"ra\":\"%s\",\"senha\":\"%s\"}", ra, senha);
            } catch (JSException e) {
                return "jsonread";
            }

            String serverResponse = LoginServer.login(userJSON);

            if (serverResponse.equalsIgnoreCase("success")) {
                response = "success";

            } else if (serverResponse.equalsIgnoreCase("isLogged")) {
                response = "isLogged";
            }
            else if (serverResponse.equalsIgnoreCase("connectionbd")) {
                response = "connectionbd";
            } else {
                response = "credential";
            }
        } else {
            response = "invalid";
        }

        return response;
    }
}

