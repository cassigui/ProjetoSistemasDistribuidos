package org.api.utils;

import java.util.ArrayList;
import java.util.List;

public class LoggedUsers {
    private static List<String> loggedUsers = new ArrayList<>();

    public static void addUser(String ra) {
        if (!isLogged(ra)) {
            loggedUsers.add(ra);
        }
    }

    public static void listUsers() {
        for (String ra : loggedUsers) {
            System.out.println(ra);
        }
    }

    public static boolean isLogged(String ra) {
        return loggedUsers.contains(ra);
    }

    public static boolean desconectUser (String ra) {
        if(isLogged(ra)) {
            loggedUsers.remove(ra);
            return true;
        }else {
            return false;
        }
    }

}
