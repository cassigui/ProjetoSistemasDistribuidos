package org.api.methodsClients;

import org.api.methodsServer.LoginServer;
import org.api.utils.Menu;

import java.sql.SQLException;

public class LoginClient {

    public static void login(String ra, String password) throws SQLException {
        String userJSON = String.format("{\"ra\":%s,\"password\":\"%s\"}", ra, password);
        LoginServer.login(userJSON);
    }

}

