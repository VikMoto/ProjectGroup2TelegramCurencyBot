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
public class CurrencyMenu {
    String checkout;
    Long chatId;
    public SendMessage getMessage() {

        String helloText = "Please choose the Currencies";

        SendMessage message = new SendMessage();
        message.setText(helloText);
        message.setChatId(chatId);

        InlineKeyboardButton usd = InlineKeyboardButton
                .builder()

                .text(this.checkout.equals("usd") ? " USD" : "USD")

                .callbackData("setCurrencyUSD")
                .build();

        InlineKeyboardButton eur = InlineKeyboardButton
                .builder()

                .text(this.checkout.equals("eur") ? "\u8364\u20AC EUR" : "EUR")

                .callbackData("setCurrencyEUR")
                .build();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Arrays.asList(usd));
        keyboard.add(Arrays.asList(eur));

        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();

        message.setReplyMarkup(keyboardMarkup);

        return message;

    }
}