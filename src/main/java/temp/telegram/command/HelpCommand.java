package temp.telegram.command;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends BotCommand {
    public HelpCommand() {
        super("help", "Help command");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage helpMessage = new SendMessage();
        helpMessage.setText("Hi! I can help you to quickly get currency Exchange Rate from most popular ukrainian banks");
        helpMessage.setChatId(Long.toString(chat.getId()));
        absSender.execute(helpMessage);
    }
}
