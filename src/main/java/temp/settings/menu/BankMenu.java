package temp.settings.menu;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import temp.currency.dto.Bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Slf4j
@AllArgsConstructor
public class BankMenu {

    String checkout;
    Long chatId;

    public SendMessage getMessage(Long chatId) {
        log.info("open Bank menu");
        String helloText = "Please choose the Bank";
        System.out.println("checkout = " + checkout);
        System.out.println("chatId = " + chatId);


        SendMessage message = new SendMessage();
        message.setText(helloText);
        message.setChatId(chatId);

        InlineKeyboardButton btn1 = InlineKeyboardButton
                .builder()

                .text(this.checkout.equals("MonoBank") ? "✅ MonoBank" : "MonoBank")

                .callbackData("setBankMonoBank")
                .build();

        InlineKeyboardButton btn2 = InlineKeyboardButton
                .builder()

                .text(this.checkout.equals("NBU") ? "✅ NBU" : "NBU")

                .callbackData("setBankNBU")
                .build();

        InlineKeyboardButton btn3 = InlineKeyboardButton
                .builder()

                .text(this.checkout.equals("PrivatBank") ? "✅ Privat" : "Privat")

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
//        message.setReplyToMessageId(messageId);

        return message;
    }


//    @Override
//    public SendMessage getMessage() {
//        log.info("open Bank menu");
//        String helloText = "Please choose the Bank";
//
//        SendMessage message = new SendMessage();
//        message.setText(helloText);
//        message.setChatId(chatId);
//
//        InlineKeyboardButton btn1 = InlineKeyboardButton
//                .builder()
//                .text(this.checkout.equals("monobank") ? "✅ MonoBank" : "MonoBank")
//                .callbackData("setBankMonoBank")
//                .build();
//
//        InlineKeyboardButton btn2 = InlineKeyboardButton
//                .builder()
//                .text(this.checkout.equals("nbu") ? "✅ NBU" : "NBU")
//                .callbackData("setBankNBU")
//                .build();
//
//        InlineKeyboardButton btn3 = InlineKeyboardButton
//                .builder()
//                .text(this.checkout.equals("privat") ? "✅ Privat" : "Privat")
//                .callbackData("setBankPrivat")
//                .build();
//
//        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
//        keyboard.add(Arrays.asList(btn1));
//        keyboard.add(Arrays.asList(btn2));
//        keyboard.add(Arrays.asList(btn3));
//
//        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
//                .builder()
//                .keyboard(keyboard)
//                .build();
//
//        message.setReplyMarkup(keyboardMarkup);
//        message.setReplyToMessageId(messageId);
//
//        return message;
//    }
}
