package org.api.methodsClients.Users;

import org.api.methodsServer.Users.DeleteServer;

import netscape.javascript.JSException;

import java.sql.SQLException;

public class DeleteClient {

    public static String excluir(String token, String ra) throws SQLException {
        String response = "invalid";

        if (token != null &&
                ra != null &&
                ra.matches("\\d{7}")) {

            if (token.equalsIgnoreCase("2578271") || token.equals(ra)) {
                try {
                    String serverResponse = DeleteServer.delete(ra);

                    if (serverResponse.equalsIgnoreCase("usernotfound")) {
                        response = "usernotfound";
                    } else if (serverResponse.equalsIgnoreCase("success")) {
                        response = "success";
                    } else if (serverResponse.equalsIgnoreCase("unauthorized")) {
                        response = "unauthorized";
                    } else if (serverResponse.equalsIgnoreCase("connectionbd")) {
                        response = "connectionbd";
                    }
                } catch (JSException e) {
                    return "jsonread";
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
