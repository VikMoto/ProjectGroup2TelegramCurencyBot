package temp.settings.menu;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class StartMenu {

    Long chatId;

    public SendMessage getMessage() {
        log.info("open Start menu");
        String helloText = "Please make Your Choice";

        System.out.println("chatId = " + chatId);

        SendMessage message = new SendMessage();
        message.setText(helloText);
        message.setChatId(chatId);

        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text("Get information")
                .callbackData("getInformation")
                .build();

        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text("Settings")
                .callbackData("settings")
                .build();



        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Arrays.asList(button1));
        keyboard.add(Arrays.asList(button2));


        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();

        message.setReplyMarkup(keyboardMarkup);
//        message.setReplyToMessageId(messageId);

        return message;
    }

}
