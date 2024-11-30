package org.api.methodsClients;
import org.api.methodsServer.LoginServer;
import org.api.methodsServer.LogoutServer;
import org.api.utils.Menu;

import java.sql.SQLException;

public class LogoutClient {
    public static void logout(String ra) throws SQLException {
        String userJSON = String.format("{\"ra\":%s}", ra);
        LogoutServer.logout(userJSON);
    }

}
