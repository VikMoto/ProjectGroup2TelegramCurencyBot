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
                .text(this.checkout.equals("USD") ? "✅ USD" : "USD")
                .callbackData("setCurrencyUSD")
                .build();

        InlineKeyboardButton eur = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("EUR") ? "✅ EUR" : "EUR")
                .callbackData("setCurrencyEUR")
                .build();

        InlineKeyboardButton back = InlineKeyboardButton
                .builder()
                .text("Back Main Menu")
                .callbackData("setCurrencyBACK")
                .build();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Arrays.asList(usd));
        keyboard.add(Arrays.asList(eur));
        keyboard.add(Arrays.asList(back));

        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();

        message.setReplyMarkup(keyboardMarkup);

        return message;

    }
}