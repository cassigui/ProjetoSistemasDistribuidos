package org.api.methodsClients.Categories;

import com.fasterxml.jackson.core.JsonProcessingException;
import netscape.javascript.JSException;
import org.api.methodsServer.Categories.CreateServerCategories;

import java.sql.SQLException;

public class CreateClientCategories {
    public static String createCategory(String token, int id, String nome) throws SQLException, JsonProcessingException {
        String response = "";

        if (nome != null) {
            String categoryJSON;
            try {
                categoryJSON = String.format("{\"id\":\"%d\",\"nome\":\"%s\"}", id, nome);
            } catch (JSException e) {
                return "jsonread";
            }

            if (token.equalsIgnoreCase("2578271")) {
                String serverResponse = CreateServerCategories.createCategory(categoryJSON);

                if (serverResponse.equalsIgnoreCase("categoryexist")) {
                    response = "categoryexist";
                } else if (serverResponse.equalsIgnoreCase("success")) {
                    response = "success";
                } else if (serverResponse.equalsIgnoreCase("connectionbd")) {
                    response = "connectionbd";
                } else if (serverResponse.equalsIgnoreCase("tableNotExist")) {
                    response = "tableNotExist";
                } else if (serverResponse.equalsIgnoreCase("error")) {
                    response = "error";
                } else if (serverResponse.equalsIgnoreCase("categoryNotExist")) {
                    response = "categoryNotExist";
                } else if (serverResponse.equalsIgnoreCase("successUpdate")) {
                    response = "successUpdate";
                }
            }else {
                response = "unauthorized";
            }
        } else {
            response = "invalid";
        }

        return response;
    }
}
