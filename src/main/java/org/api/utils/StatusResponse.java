package org.api.utils;

public class StatusResponse {
    public static void status200() {
        System.out.println("\nSucesso | Status: " + 200);
    }

    public static void status401(String response) {
        switch (response.toLowerCase()) {
            case "jsonread":
                System.out.println("\nNão foi possível realizar a leitura do JSON. | Status: " + 401 + "\n");
                break;
            case "jsonconvert":
                System.out.println("\nNão foi possível converter os dados para JSON. | Status: " + 401 + "\n");
                break;
            case "invalid":
                System.out.println("\nOs campos recebidos não são válidos. | Status: " + 401 + "\n");
                break;
            case "conectionbd":
                System.out.println("\nO servidor nao conseguiu conectar com o banco de dados. | Status: " + 401 + "\n");
                break;
            case "credential":
                System.out.println("\nCredenciais incorretas. | Status: " + 401 + "\n");
                break;
        }

    }

}
