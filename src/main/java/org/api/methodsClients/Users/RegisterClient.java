package org.api.methodsClients.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import netscape.javascript.JSException;
import org.api.methodsServer.Users.RegisterServer;

import java.sql.SQLException;

public class RegisterClient {
    public static String register(String nome, String ra, String senha) throws SQLException, JsonProcessingException {
        String response = "";

        if (
                ra != null && senha != null && nome != null &&
                        ra.matches("\\d{7}") &&
                        senha.matches("[a-zA-Z]{8,20}") &&
                        nome.matches("[A-Z ]{1,50}")) {
            String userJSON;
            try {
                userJSON = String.format(
                        "{\"nome\":\"%s\",\"ra\":\"%s\",\"senha\":\"%s\"}", nome, ra, senha);
            } catch (JSException e) {
                return "jsonread";
            }

            String serverResponse = RegisterServer.register(userJSON);

            if (serverResponse.equalsIgnoreCase("userexist")) {
                response = "userexist";
            } else if (serverResponse.equalsIgnoreCase("success")) {
                response = "success";
            } else if (serverResponse.equalsIgnoreCase("credential")) {
                response = "credential";
            } else if (serverResponse.equalsIgnoreCase("connectionbd")) {
                response = "connectionbd";
            }
        } else {
            response = "invalid";
        }

        return response;
    }
}

