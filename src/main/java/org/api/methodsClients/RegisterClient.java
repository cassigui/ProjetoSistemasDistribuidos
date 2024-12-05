package org.api.methodsClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import netscape.javascript.JSException;
import org.api.methodsServer.LoginServer;
import org.api.methodsServer.RegisterServer;
import org.api.utils.StatusResponse;

import java.sql.SQLException;

import static java.util.regex.Pattern.matches;

public class RegisterClient {
    public static String register(String nome, String ra, String senha) throws SQLException, JsonProcessingException {
        String response = "";
        String userJSON = "";

        if ((ra.length() == 7 && ra.matches("\\d{7}")) && (senha.length() >= 8 && senha.length() <= 20 && senha.matches("[a-zA-Z]{8,20}"))) {
            try {
                userJSON = String.format(
                        "{\"nome\":\"%s\",\"ra\":\"%s\",\"senha\":\"%s\"}", nome, ra, senha);
            } catch (JSException e) {
                return "jsonread";
            }
            if (RegisterServer.register(userJSON).equalsIgnoreCase("success")) {
                response = "success";
            } else if (RegisterServer.register(userJSON).equalsIgnoreCase("credential")) {
                response = "credential";
            } else if (RegisterServer.register(userJSON).equalsIgnoreCase("connectionbd")) {
                response = "conectionbd";
            }
        } else {
            response = "invalid";
        }

        return response;

    }

}
