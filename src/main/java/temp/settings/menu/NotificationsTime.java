package temp.settings.menu;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NotificationsTime {
    String checkout;
    Long chatId;

    public SendMessage getMessage() {
        String helloText = "Please choose the notification time";

        SendMessage message = new SendMessage();
        message.setText(helloText);
        message.setChatId(chatId);

        InlineKeyboardButton nine = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("9") ? "\uD83D\uDD58 9:00" : "9:00")
                .callbackData("noticeTime9")
                .build();

        InlineKeyboardButton ten = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("10") ? "\uD83D\uDD59 10:00" : "10:00")
                .callbackData("noticeTime10")
                .build();

        InlineKeyboardButton eleven = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("11") ? "\uD83D\uDD5A 11:00" : "11:00")
                .callbackData("noticeTime11")
                .build();

        InlineKeyboardButton twelve = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("12") ? "\uD83D\uDD5B 12:00" : "12:00")
                .callbackData("noticeTime12")
                .build();

        InlineKeyboardButton thirteen = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("13") ? "\uD83D\uDD50 13:00" : "13:00")
                .callbackData("noticeTime13")
                .build();

        InlineKeyboardButton fourteen = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("14") ? "\uD83D\uDD51 14:00" : "14:00")
                .callbackData("noticeTime14")
                .build();

        InlineKeyboardButton fifteen = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("15") ? "\uD83D\uDD52 15:00" : "15:00")
                .callbackData("noticeTime15")
                .build();

        InlineKeyboardButton sixteen = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("16") ? "\uD83D\uDD53 16:00" : "16:00")
                .callbackData("noticeTime16")
                .build();

        InlineKeyboardButton seventeen = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("17") ? "\uD83D\uDD54 17:00" : "17:00")
                .callbackData("noticeTime17")
                .build();

        InlineKeyboardButton eighteen = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("18") ? "\uD83D\uDD55 18:00" : "18:00")
                .callbackData("noticeTime18")
                .build();

        InlineKeyboardButton nineteen = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("19") ? "\uD83D\uDD55 19:00" : "19:00")
                .callbackData("noticeTime19")
                .build();
        InlineKeyboardButton twenty = InlineKeyboardButton
                .builder()
                .text(this.checkout.equals("20") ? "\uD83D\uDD55 20:00" : "20:00")
                .callbackData("noticeTime20")
                .build();


        InlineKeyboardButton cancelNotifications = InlineKeyboardButton
                .builder()
                .text("Cancel Notifications")
                .callbackData("noticeTimeCancelNotifications")
                .build();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(nine);
        keyboardButtonsRow1.add(ten);
        keyboardButtonsRow1.add(eleven);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(twelve);
        keyboardButtonsRow2.add(thirteen);
        keyboardButtonsRow2.add(fourteen);
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(fifteen);
        keyboardButtonsRow3.add(sixteen);
        keyboardButtonsRow3.add(seventeen);
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(eighteen);
        keyboardButtonsRow4.add(nineteen);
        keyboardButtonsRow4.add(twenty);
        keyboardButtonsRow4.add(cancelNotifications);


        List<List<InlineKeyboardButton>> settingsKeyboard = new ArrayList<>();
        settingsKeyboard.add(keyboardButtonsRow1);
        settingsKeyboard.add(keyboardButtonsRow2);
        settingsKeyboard.add(keyboardButtonsRow3);
        settingsKeyboard.add(keyboardButtonsRow4);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(settingsKeyboard);
        message.setReplyMarkup(markup);
        return message;
    }
}
