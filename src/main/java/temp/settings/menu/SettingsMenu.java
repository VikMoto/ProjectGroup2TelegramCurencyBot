package temp.settings.menu;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
public class SettingsMenu {
    Long chatId;

    public SendMessage getMessage() {


        String helloText = "Please make Your Choice";

        System.out.println("chatId = " + chatId);

        SendMessage message = new SendMessage();
        message.setText(helloText);
        message.setChatId(chatId);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text("Price precision")
                .callbackData("price precision")
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text("Bank")
                .callbackData("bank")
                .build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder()
                .text("Currencies")
                .callbackData("currencies")
                .build();
        InlineKeyboardButton button4 = InlineKeyboardButton.builder()
                .text("Time notification")
                .callbackData("time notification")
                .build();


        keyboard.add(Arrays.asList(button1));
        keyboard.add(Arrays.asList(button2));
        keyboard.add(Arrays.asList(button3));
        keyboard.add(Arrays.asList(button4));


        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();

        message.setReplyMarkup(keyboardMarkup);

        return message;
    }
}
