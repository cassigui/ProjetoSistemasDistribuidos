package org.api.methodsClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import netscape.javascript.JSException;
import org.api.methodsServer.LoginServer;
import org.api.utils.Menu;
import org.api.utils.StatusResponse;

import java.sql.SQLException;

public class LoginClient {

    public static String login(String ra, String senha) throws SQLException {
        String response = "";
        String userJSON = "";

        if ((ra.length() == 7 && ra.matches("\\d{7}")) && (senha.length() >= 8 && senha.length() <= 20 && senha.matches("[a-zA-Z]{8,20}"))) {
            try {
                userJSON = String.format("{\"ra\":%s,\"senha\":\"%s\"}", ra, senha);
            } catch (JSException e) {
                return  "jsonread";
            }
            if (LoginServer.login(userJSON).equalsIgnoreCase("success")) {
                response = "success";
            } else if (LoginServer.login(userJSON).equalsIgnoreCase("connectionbd")) {
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

