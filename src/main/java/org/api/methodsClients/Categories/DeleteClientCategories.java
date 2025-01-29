package org.api.methodsClients.Categories;
import org.api.methodsServer.Categories.DeleteServerCategories;

import netscape.javascript.JSException;

import java.sql.SQLException;

public class DeleteClientCategories {

    public static String excluir(String token, int id) throws SQLException {
        String response = "invalid";

        if (token.equalsIgnoreCase("2578271")) {

            if (token.equalsIgnoreCase("2578271")) {
                try {
                    String serverResponse = DeleteServerCategories.delete(id);

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