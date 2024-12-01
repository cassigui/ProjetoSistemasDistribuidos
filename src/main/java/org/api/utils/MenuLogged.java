package org.api.utils;

public class MenuLogged {
    public static void menuLogged(String name) {
//        StatusResponse.status200();
        System.out.println("Usuario " + name + " Logado");
        System.out.println("Digite (\"exit\" para desligar a aplicação)");
        System.out.println("Digite (\"3\" para sair da sua conta)\n");
    }
}
