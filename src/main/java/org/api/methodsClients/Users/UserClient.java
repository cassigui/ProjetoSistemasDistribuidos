package org.api.methodsClients.Users;

import org.api.methodsServer.Users.UserServer;

import java.sql.SQLException;

public class UserClient {

    public static String localizar(String token, String ra) throws SQLException {
        String response = "";
        if (token.equalsIgnoreCase("2578271") || token.equals(ra)) {
            try {
                String serverResponse = UserServer.findUser(ra);

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
