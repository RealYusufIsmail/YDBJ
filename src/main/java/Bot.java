import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ydb.YDB;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        new YDB(args);
    }
}
