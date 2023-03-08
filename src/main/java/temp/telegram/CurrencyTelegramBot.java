package temp.telegram;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import temp.currency.CurrencyService;
import temp.currency.PrivatBankCurrencyService;
import temp.currency.dto.Currency;
import temp.telegram.command.HelpCommand;
import temp.telegram.command.StartCommand;
import temp.ui.PrettyPrintCurrencyServise;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    private CurrencyService currencyService;
    private PrettyPrintCurrencyServise prettyPrintCurrencyServise;
    public CurrencyTelegramBot() {
        currencyService = new PrivatBankCurrencyService();
        prettyPrintCurrencyServise = new PrettyPrintCurrencyServise();

        register(new StartCommand());
        register(new HelpCommand());
    }

    @Override
    public String getBotUsername() {
        return BotConstants.getBotName();
    }

    @Override
    public String getBotToken() {
        return BotConstants.getBotToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }



    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {

            CallbackQuery callbackQuery = update.getCallbackQuery();
            System.out.println("callbackQuery = " + callbackQuery.getData());

            CallbackQuery query = update.getCallbackQuery();
            String callbackData = query.getData();
            Long chatId = query.getMessage().getChatId();
            Integer messageId = query.getMessage().getMessageId();

            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();

            answerCallbackQuery.setCallbackQueryId(query.getId());
            handle(callbackQuery, callbackData, chatId, messageId, answerCallbackQuery);


            try {
                execute(answerCallbackQuery);
            } catch (Exception e) {
                // Handle exception
            }

        }

    }

    private void handle(CallbackQuery callbackQuery, String callbackData, Long chatId, Integer messageId, AnswerCallbackQuery answerCallbackQuery) {
        switch (callbackData) {
            case "get notification":
                //todo another sendOptionsMessage2()
//                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose Отримати інфо");
                break;
            case "settings":
                sendOptionsMessage(chatId, messageId, "Налаштування:");
                answerCallbackQuery.setText("You chose налаштування");
                break;
            case "price precision":
                sendOptionsMessage(chatId, messageId, "Налаштування:");
                answerCallbackQuery.setText("You chose налаштування");
                break;
            case "bank":
                //                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose Отримати інфо");
                break;
            case "currencies":
                //                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose Отримати інфо");
                break;
            case "time notification":
                //                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose Отримати інфо");
                break;
            case "start":
                // If callback data is "start", send the start message again
                StartCommand startCommand = new StartCommand();
                startCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
                break;


        }
    }

    private void sendOptionsMessage(Long chatId, Integer messageId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

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
        InlineKeyboardButton button5 = InlineKeyboardButton.builder()
                .text("Return main manu")
                .callbackData("start")
                .build();

        keyboard.add(Arrays.asList(button1));
        keyboard.add(Arrays.asList(button2));
        keyboard.add(Arrays.asList(button3));
        keyboard.add(Arrays.asList(button4));
        keyboard.add(Arrays.asList(button5));

        final InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();

        message.setReplyMarkup(keyboardMarkup);
        message.setReplyToMessageId(messageId);
        try {
            execute(message);
        } catch (Exception e) {
            // Handle exception
        }
    }
    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }
}
