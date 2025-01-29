package org.api.methodsClients.Categories;

import org.api.methodsServer.Categories.ListServerCategories;

import java.sql.SQLException;

public class ListClientCategories {
    public static String listar(String token) throws SQLException {
        String response = "";
        if (token.equalsIgnoreCase("2578271")) {
            try {
                String serverResponse = ListServerCategories.listCategories();

                if (serverResponse.equalsIgnoreCase("connectionbd")) {
                    response = "connectionbd";
                } else {
                    response = serverResponse;
                }
            } catch (Exception e) {
                response = "error";
            }
        } else {
            response = "unauthorized";
        }

        return response;
    }
}
