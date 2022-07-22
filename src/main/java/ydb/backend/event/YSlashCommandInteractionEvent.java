package ydb.backend.event;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class YSlashCommandInteractionEvent extends SlashCommandInteractionEvent {
    public YSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
        super(event.getJDA(), event.getResponseNumber(), event);
    }

    public void qReply(@NotNull String message) {
        this.reply(message).queue();
    }

    public void qReply(MessageEmbed embed) {
        this.replyEmbeds(embed).queue();
    }

    public void qReplyEphemeral(@NotNull String message) {
        this.reply(message).setEphemeral(true).queue();
    }

    public void qReplyEphemeral(MessageEmbed embed) {
        this.replyEmbeds(embed).setEphemeral(true).queue();
    }
}
