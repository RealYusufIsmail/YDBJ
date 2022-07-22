package ydb;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class YDB extends ListenerAdapter {
    public YDB(String[] args) throws LoginException {
        // TODO code application logic here

        JDA jda = JDABuilder.createDefault(BotConfig.getToken(), EnumSet.allOf(GatewayIntent.class))
                .enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.EMOJI, CacheFlag.MEMBER_OVERRIDES)
                .setActivity(Activity.listening("to your commands"))
                .build();
    }
}
