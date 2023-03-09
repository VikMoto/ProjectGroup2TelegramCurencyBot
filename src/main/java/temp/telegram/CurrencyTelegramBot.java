package temp.telegram;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import temp.Api.CurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Currency;
import temp.telegram.command.HelpCommand;
import temp.telegram.command.StartCommand;
import temp.ui.PrettyPrintCurrencyServise;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.io.IOException;
import java.util.*;

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

    private void handle(CallbackQuery callbackQuery, String callbackData, Long chatId, Integer messageId, AnswerCallbackQuery answerCallbackQuery) throws IOException {
        switch (callbackData) {
            case "getInformation":
                //static service in code for now
                SendMessage message = new SendMessage();
                int presicion = 3;
                CurrencyService currencyService = new PrivatBankCurrencyService();
                Currency currency = Currency.USD;
                String formatedRate = new PrettyPrintCurrencyServise().convert(currencyService.getRate(currency), currency, presicion);
                message.setText(formatedRate);
                answerCallbackQuery.setText(formatedRate);
                break;
            case "settings":
                sendOptionsMessage(chatId, messageId, "settings");
                answerCallbackQuery.setText("You chose settings");
                break;
            case "price precision":
                sendOptionsMessagePricePrecision(chatId, messageId, "price precision");
                answerCallbackQuery.setText("You chose price precision");
                break;
            case "bank":
                //                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose bank");
                break;
            case "currencies":
                //                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose currencies");
                break;
            case "time notification":
                //                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose time notification");
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
        private void sendOptionsMessagePricePrecision(Long chatId, Integer messageId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text("2")
                .callbackData("2")
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text("3")
                .callbackData("3")
                .build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder()
                .text("4")
                .callbackData("4")
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
