package org.api.utils;

public class StatusResponse {
    public static String status200(String operation, String ra) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"operation\":\"%s\",\"status\":\"%s\",\"ra\":\"%s\"}",
                operation, "200", ra
        );
        return responseJSON;
    }

    public static String status401(String operation, String response) {
        String responseJSON = "";
        switch (response.toLowerCase()) {
            case "jsonread":
                responseJSON = String.format(
                        "{\"status\":\"%s\",\"operation\":\"%s\",\"message\":\"%s\"}",
                        "401", operation, "Não foi possível ler o json recebido."
                );
                break;
            case "jsonconvert":
                responseJSON = String.format(
                        "{\"status\":\"%s\",\"operation\":\"%s\",\"message\":\"%s\"}",
                        "401", operation, "Não foi possível converter os dados para JSON.."
                );
                break;
            case "invalid":
                responseJSON = String.format(
                        "{\"status\":\"%s\",\"operation\":\"%s\",\"message\":\"%s\"}",
                        "401", operation, "Os campos recebidos não são válidos."
                );
                break;
            case "connectionbd":
                responseJSON = String.format(
                        "{\"status\":\"%s\",\"operation\":\"%s\",\"message\":\"%s\"}",
                        "401", operation, "O servidor nao conseguiu conectar com o banco de dados"
                );
                break;
            case "credential":
                responseJSON = String.format(
                        "{\"status\":\"%s\",\"operation\":\"%s\",\"message\":\"%s\"}",
                        "401", operation, "Credenciais incorretas"
                );
        }
        return responseJSON;
    }

}
