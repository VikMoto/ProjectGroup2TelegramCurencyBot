package temp.telegram.command;

import temp.currency.dto.Currency;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StartCommand extends BotCommand {
    public StartCommand() {
        super("start"," Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
       String text = "What currency Exchange Rate do You want to know:";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(Long.toString(chat.getId()));
        //take from enum Currency USD and EUR and convert to buttons Array
        final List<InlineKeyboardButton> buttons = Arrays.asList(Currency.USD, Currency.EUR)
                .stream()
                .map(it -> it.name())
                .map(it -> InlineKeyboardButton
                        .builder()
                        .text(it)
                        .callbackData(it)
                        .build())
                .collect(Collectors.toList());
        /** create keyboard from List */
        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singleton(buttons))
                .build();

        sendMessage.setReplyMarkup(keyboardMarkup);

        //create button
//        final InlineKeyboardButton usdButton = InlineKeyboardButton
//                .builder()
//                .text("USD")
//                .callbackData("USD")
//                .build();
//
//        KeyboardButton usdButton = KeyboardButton.builder().text("USD").build();
//        //create keyboard ONE BUTTON
//        final InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
//                .builder()
//                //keyboard this is List of Lists
//                .keyboard(Collections.singleton( //one List
//                        Collections.singletonList(usdButton) //Put to inside another List
//                ))
//                .build();
//        sendMessage.setReplyMarkup(keyboard);




//        KeyboardRow keyboardRow = new KeyboardRow();
//        keyboardRow.add(usdButton);
////create keyboard and sout to bot
//        ReplyKeyboardMarkup keyboard = ReplyKeyboardMarkup
//                .builder()
//                .keyboardRow(keyboardRow)
//                .build();
//        sendMessage.setReplyMarkup(keyboard);

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        System.out.println("Start pressed!");
    }
}
