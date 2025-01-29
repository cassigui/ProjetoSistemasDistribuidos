package org.api.methodsClients.Users;

import org.api.methodsServer.Users.UsersServer;

import java.sql.SQLException;

public class UsersClient {

    public static String listar(String token) throws SQLException {
        String response = "";
        if (token.equalsIgnoreCase("2578271")) {
            try {
                String serverResponse = UsersServer.listUsers();

                if (serverResponse.equalsIgnoreCase("connectionbd")) {
                    response = "connectionbd";
                } else {
                    response = serverResponse;
                }
            } catch (Exception e) {
                response = "error";
            }
        }else {
            response = "unauthorized";
        }

        return response;
    }
}
