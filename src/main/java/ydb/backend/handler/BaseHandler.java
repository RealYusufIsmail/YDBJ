package ydb.backend.handler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public abstract class BaseHandler extends ListenerAdapter {
    /**
     *
     * @return used to set the bot owner id.
     */
    protected abstract long botOwnerId();


    public static void checkIfBuildIsNull(Object build) {
        if (Objects.isNull(build)) {
            throw new IllegalArgumentException("Build is null");
        }
    }
}
