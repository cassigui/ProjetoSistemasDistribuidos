package org.api.methodsClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.api.methodsServer.RegisterServer;
import org.api.utils.StatusResponse;

import java.sql.SQLException;

import static java.util.regex.Pattern.matches;

public class RegisterClient {
    public static boolean register(String name, String ra, String password) throws SQLException, JsonProcessingException {
        Boolean register = false;
        String userJSON = "";

        if (ra.length() == 7 && ra.matches("\\d{7}")) {
            userJSON = String.format(
                    "{\"name\":\"%s\",\"ra\":\"%s\",\"password\":\"%s\"}", name, ra, password);
            if (RegisterServer.register(userJSON)) {
                register = true;
//                System.out.println("Server: " + StatusResponse.status200());
                System.out.println("Server: " + userJSON + " Cadastrado com Sucesso");
            }else {
//                System.out.println("Server: " + StatusResponse.status401("credential"));
            }
        } else {
//            System.out.println("Server: " + StatusResponse.status401("invalid"));
        }

        return register;
    }
}
