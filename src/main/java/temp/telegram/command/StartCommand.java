package temp.telegram.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import temp.settings.BotUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartCommand extends BotCommand {
    public StartCommand() {
        super("start", " Start command");
    }

    BotUserService service = BotUserService.getInstance();

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        service.createUser(chat.getId());

        String text = "Welcome! Current bot will help you to get information about currency exchange rate";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(Long.toString(chat.getId()));

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text("Get information")
                .callbackData("getInformation")
                .build();

        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text("Settings")
                .callbackData("settings")
                .build();

        keyboard.add(Arrays.asList(button1));
        keyboard.add(Arrays.asList(button2));

        // create keyboard from List
        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();

        sendMessage.setReplyMarkup(keyboardMarkup);
        try {
            absSender.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
