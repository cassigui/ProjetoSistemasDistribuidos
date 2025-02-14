package org.api.methodsClients.Categories;

import org.api.methodsServer.Categories.ListServerCategories;
import org.api.methodsServer.Users.UserServer;

import java.sql.SQLException;

public class ListClientCategories {
    public static String listar(String token) throws SQLException {
        String response = "";
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

        return response;
    }

    public static String localizar(String token, String ra, int id) throws SQLException {
        String response = "";
            try {
                String serverResponse = ListServerCategories.findCategory(id);

                if (serverResponse.equalsIgnoreCase("connectionbd")) {
                    response = "connectionbd";
                } else {
                    response = serverResponse;
                }
            } catch (Exception e) {
                response = "error";
            }
        return response;
    }
}
