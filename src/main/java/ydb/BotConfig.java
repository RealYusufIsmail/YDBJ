package ydb;

import io.github.yusufsdiscordbot.config.Config;

public class BotConfig {

    public static String getToken() {
        return Config.getString("TOKEN");
    }
}
