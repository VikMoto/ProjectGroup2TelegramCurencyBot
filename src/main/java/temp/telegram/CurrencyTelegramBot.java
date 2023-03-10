package temp.telegram;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import temp.Api.CurrencyService;


import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;

import temp.settings.menu.Bank;

import temp.settings.BotUserService;

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



    private void handle(CallbackQuery callbackQuery, String callbackData, Long chatId, Integer messageId, AnswerCallbackQuery answerCallbackQuery) throws IOException, TelegramApiException {

        BotUserService service = BotUserService.getInstance();


        switch (callbackData) {
            case "getInformation":
                //static service in code for now

                service.setPrecision(chatId, 3);
                service.setBank(chatId, Bank.NBU);
                service.setCurrencies(chatId, Currency.USD);
                Currency currency = service.getCurrency(chatId);
                System.out.println("currency = " + currency);

                String serviceInfo = service.getInfo(chatId);

//                CurrencyService currencyService = new PrivatBankCurrencyService();
//                Currency currency = Currency.USD;
//                String formatedRate = new PrettyPrintCurrencyServise().convert(currencyService.getRate(currency), currency, service.getPrecision(chatId));
//                System.out.println("serviceInfo = " + serviceInfo);
//                answerCallbackQuery.setText("serviceInfo = " + serviceInfo);
////                answerCallbackQuery.setText("formatedRate = " + formatedRate);



                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(serviceInfo);
                sendMessage.setChatId(Long.toString(chatId));
                execute(sendMessage);

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
                Bank bank = new Bank();
                try {
                    execute(bank.getMessage(chatId, messageId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                answerCallbackQuery.setText("You chose bank");
                break;
            case "currency":
                //                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose currency");
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

    public SendMessage getMessage(Long chatId) throws IOException {
        BotUserService botUserService = BotUserService.getInstance();


        String helloText = botUserService.getInfo(chatId);
        SendMessage message = new SendMessage();//same code
        message.setText(helloText);
        message.setChatId(Long.toString(chatId));

        InlineKeyboardButton getInfo = InlineKeyboardButton
                .builder()
                .text("Get info \uD83D\uDCB1")
                .callbackData("getInfo")
                .build();

        InlineKeyboardButton settings = InlineKeyboardButton
                .builder()
                .text("Settings \ud83d\udd27")
                .callbackData("settings")
                .build();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList();
        keyboardButtonsRow1.add(getInfo);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList();
        keyboardButtonsRow2.add(settings);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList();
        keyboard.add(keyboardButtonsRow1);
        keyboard.add(keyboardButtonsRow2);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
        return message;
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
