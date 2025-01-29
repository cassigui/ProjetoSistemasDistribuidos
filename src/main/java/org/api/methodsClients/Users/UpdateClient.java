package org.api.methodsClients.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import netscape.javascript.JSException;
import org.api.methodsServer.Users.UpdateServer;

import java.sql.SQLException;

public class UpdateClient {
    public static String update(String token, String nome, String ra, String senha) throws SQLException, JsonProcessingException {
        String response = "";
        String userJSON = "";

        if
        (token != null &&
                ra != null && senha != null && nome != null &&
                ra.matches("\\d{7}") &&
                senha.matches("[a-zA-Z]{8,20}") &&
                nome.matches("[A-Z ]{1,50}")) {
            try {
                userJSON = String.format(
                        "{\"nome\":\"%s\",\"ra\":\"%s\",\"senha\":\"%s\"}",
                        nome, ra, senha
                );
            } catch (JSException e) {
                return "jsonread";
            }

            if (token.equalsIgnoreCase("2578271") || token.equals(ra)) {
                String serverResponse = UpdateServer.update(userJSON);

                if (serverResponse.equalsIgnoreCase("usernotfound")) {
                    response = "usernotfound";
                } else if (serverResponse.equalsIgnoreCase("success")) {
                    response = "success";
                } else if (serverResponse.equalsIgnoreCase("unauthorized")) {
                    response = "unauthorized";
                } else if (serverResponse.equalsIgnoreCase("connectionbd")) {
                    response = "connectionbd";
                }
            } else {
                response = "unauthorized";
            }
        } else {
            response = "invalid";
        }

        return response;
    }
}
