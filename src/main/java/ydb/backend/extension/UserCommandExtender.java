package ydb.backend.extension;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.jetbrains.annotations.NotNull;
import ydb.backend.builder.user.UserCommand;

import javax.annotation.Nonnull;

public abstract class UserCommandExtender {

    public abstract void onUserContextInteraction(@NotNull UserContextInteractionEvent event);

    public void onButtonClick(ButtonInteractionEvent event) {

    }

    public void onModalInteraction(ModalInteractionEvent event) {

    }

    public void onCommandAutoComplete(@Nonnull CommandAutoCompleteInteractionEvent event) {

    }

    public void onSelectMenu(@Nonnull SelectMenuInteractionEvent event) {

    }

    public abstract UserCommand build();
}
