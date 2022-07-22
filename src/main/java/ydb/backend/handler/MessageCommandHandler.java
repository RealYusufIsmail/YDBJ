package ydb.backend.handler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ydb.backend.extension.MessageCommandExtender;

import javax.annotation.Nonnull;
import java.util.*;

public abstract class MessageCommandHandler extends BaseHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageCommandHandler.class);
    private final Map<String, MessageCommandExtender> messageCommand = new HashMap<>();

    private static final String COMMAND_ERROR = "The command {} is not registered";

    /**
     * Used to determine whether the commands should be global or guild only.
     */
    private final @NotNull CommandListUpdateAction globalCommandsData;
    private final @NotNull CommandListUpdateAction guildCommandsData;
    private final JDA jda;

    protected MessageCommandHandler(@NotNull JDA jda, @NotNull Guild guild) {
        globalCommandsData = jda.updateCommands();
        guildCommandsData = guild.updateCommands();
        this.jda = jda;
    }

    /**
     * Used to register slash commands. when the developer types messageCommand.add(new
     * ExampleCommand());. The addCommand will retrieve the commandData which includes
     * name,description,options,sub commands, etc
     *
     * @param command <br>
     *        The Command class is an interface class which contains all the need methods for the
     *        making of the command. <br>
     *        <br>
     *        The boolean {@link MessageCommandExtender#build#isGuildOnly()} is used to determine
     *        whether the command should be global or guild only. determines whether the command
     *        should be Global or Guild only.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void addMessageCommand(@NotNull MessageCommandExtender command) {

        BaseHandler.checkIfBuildIsNull(command.build());

        messageCommand.put(command.build().getCommandData().getName(), command);
        if (command.build().isGuildOnly()) {
            guildCommandsData.addCommands(command.build().getCommandData());
        } else {
            globalCommandsData.addCommands(command.build().getCommandData());
        }
    }

    /**
     * Used to register the slash commands.
     *
     * @param messageCommands the slash commands.
     */
    public void queueAndRegisterMessageCommands(
            @NotNull Collection<MessageCommandExtender> messageCommands) {
        messageCommands.forEach(this::addMessageCommand);
        onFinishedRegistration();
    }

    /**
     * Queues the command after the command has been registered.
     */
    private void onFinishedRegistration() {
        globalCommandsData.queue();
        guildCommandsData.queue();
    }


    /**
     * Handles the slash command event.
     *
     * @param event The original slash command event,
     */
    @Override
    public void onMessageContextInteraction(@Nonnull MessageContextInteractionEvent event) {
        final MessageCommandExtender cmd = messageCommand.get(event.getName());

        if (cmd.build().isOwnerOnly() && event.getUser().getIdLong() != botOwnerId()) {
            event.reply("You do not have permission to use this command.")
                .setEphemeral(true)
                .queue();
            return;
        }

        if (cmd.build().getUserPerms() != null && cmd.build().getBotPerms() != null &&
                event.getMember().hasPermission(cmd.build().getUserPerms()) &&
                event.getGuild().getSelfMember().hasPermission(cmd.build().getBotPerms())) {
            event.reply("You or the bot do not have the required permissions to use this command.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (cmd.build().getUserPerms() != null
                && event.getMember().hasPermission(cmd.build().getUserPerms())) {
            event.reply("You do not have permission to use this command.")
                .setEphemeral(true)
                .queue();
        } else {
            cmd.onMessageContextInteraction(event);
        }

        if (cmd.build().getBotPerms() != null
                && event.getGuild().getSelfMember().hasPermission(cmd.build().getBotPerms())) {
            event.reply("I do not have permission to use this command.").setEphemeral(true).queue();
        } else {
            cmd.onMessageContextInteraction(event);
        }
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        final MessageCommandExtender cmd = messageCommand.get(event.getComponentId());
        cmd.onButtonClick(event);
    }

    @Override
    public void onSelectMenuInteraction(@Nonnull SelectMenuInteractionEvent event) {
        final MessageCommandExtender cmd = messageCommand.get(event.getComponentId());
        cmd.onSelectMenu(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(
            @Nonnull CommandAutoCompleteInteractionEvent event) {
        final MessageCommandExtender cmd = messageCommand.get(event.getName());
        cmd.onCommandAutoComplete(event);
    }

    @Override
    public void onModalInteraction(@Nonnull ModalInteractionEvent event) {
        final MessageCommandExtender cmd = messageCommand.get(event.getModalId());
        cmd.onModalInteraction(event);
    }

    /**
     * Gets slash commands as a list.
     *
     * @return retrieves the commands as a list.
     */
    @NotNull
    public List<MessageCommandExtender> getMessageCommands() {
        return new ArrayList<>(this.messageCommand.values());
    }

}
