package ydb.backend.extension;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.jetbrains.annotations.NotNull;
import ydb.backend.builder.slash.SlashCommand;
import ydb.backend.event.YSlashCommandInteractionEvent;

import javax.annotation.Nonnull;

public abstract class SlashCommandExtender {

    // This method is called when the command is executed.
    public abstract void onSlashCommandInteraction(@NotNull YSlashCommandInteractionEvent event);

    public void onButtonClick(ButtonInteractionEvent event) {

    }

    public void onModalInteraction(ModalInteractionEvent event) {

    }

    public void onCommandAutoComplete(@Nonnull CommandAutoCompleteInteractionEvent event) {

    }

    public void onSelectMenu(@Nonnull SelectMenuInteractionEvent event) {

    }

    public abstract SlashCommand build();
}
