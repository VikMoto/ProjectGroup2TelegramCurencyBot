package temp.settings.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import temp.currency.dto.Bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankMenu {
    public SendMessage getMessage(Long chatId, Integer messageId, Bank bank) {
        String helloText = "Please choose the Bank";
        SendMessage message = new SendMessage();
        message.setText(helloText);
        message.setChatId(chatId);

        InlineKeyboardButton btn1 = InlineKeyboardButton
                .builder()
                .text(bank.name().equals("MonoBank") ? "✅ MonoBank" : "MonoBank")
                .callbackData("setBankMonoBank")
                .build();

        InlineKeyboardButton btn2 = InlineKeyboardButton
                .builder()
                .text(bank.name().equals("NBU") ? "✅ NBU" : "NBU")
                .callbackData("setBankNBU")
                .build();

        InlineKeyboardButton btn3 = InlineKeyboardButton
                .builder()
                .text(bank.name().equals("PrivatBank") ? "✅ PrivatBank" : "PrivatBank")
                .callbackData("setBankPrivat")
                .build();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Arrays.asList(btn1));
        keyboard.add(Arrays.asList(btn2));
        keyboard.add(Arrays.asList(btn3));

        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();

        message.setReplyMarkup(keyboardMarkup);
        message.setReplyToMessageId(messageId);

        return message;
    }
}
