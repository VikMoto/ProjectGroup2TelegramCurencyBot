package temp.settings.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PricePrecisionMenu {
    public SendMessage getMessage(Long chatId, Integer messageId, int precision ) {
        String helloText = "Please select the rounding precision: ";
        SendMessage message = new SendMessage();
        message.setText(helloText);
        message.setChatId(chatId);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text(precision == 2 ? "✅ 2" : "2")
                .callbackData("pricePrecision2")
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text(precision == 3 ? "✅ 3" : "3")
                .callbackData("pricePrecision3")
                .build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder()
                .text(precision == 4 ? "✅ 4" : "4")
                .callbackData("pricePrecision4")
                .build();

        keyboard.add(Arrays.asList(button1));
        keyboard.add(Arrays.asList(button2));
        keyboard.add(Arrays.asList(button3));

        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();

        message.setReplyMarkup(keyboardMarkup);
        message.setReplyToMessageId(messageId);

        return message;
    }
}
