package temp.telegram;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import temp.Api.CurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Bank;
import temp.settings.menu.NotificationsTime;
import temp.settings.BotUserService;
import temp.settings.menu.BankMenu;
import temp.settings.menu.PricePrecisionMenu;
import temp.settings.menu.SettingsMenu;
import temp.settings.menu.StartMenu;
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
        SendMessage answer;
        BotUserService service = BotUserService.getInstance();
        StartCommand startCommand = new StartCommand();
        switch (callbackData) {
            case "getInformation":
                handleGetInformation(chatId, service);
                break;
            case "settings":
                handleSettingsMainMenu(chatId, answerCallbackQuery);
                break;
            case "price precision":
                handlePricePrecision(chatId, answerCallbackQuery, service);
                break;
            case "pricePrecision2":
                handlePricePrecisionSelection(chatId, 2,  service);
                break;
            case "pricePrecision3":
                handlePricePrecisionSelection(chatId, 3,  service);
                break;
            case "pricePrecision4":
                handlePricePrecisionSelection(chatId, 4,  service);
                break;
            case "bank":
                handleBankSelection(chatId, answerCallbackQuery, service);
                break;
            case "setBankNBU":
                handleSetBankSelection(chatId, service, Bank.NBU);
                break;
            case "setBankMonoBank":
                handleSetBankSelection(chatId, service, Bank.MonoBank);
                break;
            case "setBankPrivat":
                handleSetBankSelection(chatId, service, Bank.PrivatBank);
                break;

            case "currency":
                //                    sendOptionsMessage2(chatId, messageId, "You chose Отримати інфо. Choose another value:");
                answerCallbackQuery.setText("You chose currency");
                break;

            case "time notification":
                NotificationsTime notificationsTime = new NotificationsTime(String.valueOf(service.getSchedulerTime(chatId)),chatId);
                execute(notificationsTime.getMessage());
                break;
            case "cancelNotifications":
                service.setScheduler(chatId, false);
                //todo remove service.getScheduler(chatId) after all test
                getAnswerMessage(chatId, "You Cancel Notifications " + service.getScheduler(chatId));
                execute(new StartMenu(chatId).getMessage());
                break;
            case "9":
                handleSchedulerTimeSelection(chatId, 9,  service);
                break;
            case "10":
                handleSchedulerTimeSelection(chatId, 10,  service);
                break;
            case "11":
                handleSchedulerTimeSelection(chatId, 11,  service);
                break;
            case "12":
                handleSchedulerTimeSelection(chatId, 12,  service);
                break;
            case "13":
                handleSchedulerTimeSelection(chatId, 13,  service);
                break;
            case "14":
                handleSchedulerTimeSelection(chatId, 14,  service);
                break;
            case "15":
                handleSchedulerTimeSelection(chatId, 15,  service);
                break;
            case "16":
                handleSchedulerTimeSelection(chatId, 16,  service);
                break;
            case "17":
                handleSchedulerTimeSelection(chatId, 17,  service);
                break;
            case "18":
                handleSchedulerTimeSelection(chatId, 18,  service);
                break;


        }
    }

    private void handleSchedulerTimeSelection(Long chatId, int time, BotUserService service) throws TelegramApiException {
        service.setSchedulerTime(chatId, time);
        service.setScheduler(chatId, true);
        //todo remove " time notifications is " + service.getScheduler(chatId) after all tests
        getAnswerMessage(chatId, "You set Time as " + service.getSchedulerTime(chatId) +
                " time notifications is " + service.getScheduler(chatId));
        execute(new StartMenu(chatId).getMessage());
    }

    private void handleSettingsMainMenu(Long chatId, AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException {
        execute(new SettingsMenu(chatId).getMessage());
        answerCallbackQuery.setText("You chose settings");
    }

    private void handleBankSelection(Long chatId, AnswerCallbackQuery answerCallbackQuery, BotUserService service) throws TelegramApiException {
        BankMenu bank = new BankMenu(service.getBank(chatId).name(), chatId);
        execute(bank.getMessage(chatId));
        answerCallbackQuery.setText("You chose bank");
    }

    private void handleSetBankSelection(Long chatId, BotUserService service, Bank bank) throws TelegramApiException {
        service.setBank(chatId,bank);
        getAnswerMessage(chatId, "You set Bank as " + service.getBank(chatId).name());
        execute(new StartMenu(chatId).getMessage());
    }

    private void handlePricePrecisionSelection(Long chatId, int precision, BotUserService service) throws TelegramApiException {
        service.setPrecision(chatId, precision);
        getAnswerMessage(chatId, "Number of decimal places: " + service.getPrecision(chatId));
        execute(new StartMenu(chatId).getMessage());
    }

    private void handlePricePrecision(Long chatId, AnswerCallbackQuery answerCallbackQuery, BotUserService service) throws TelegramApiException {
        PricePrecisionMenu pricePrecisionMenu = new PricePrecisionMenu();
        execute(pricePrecisionMenu.getMessage(chatId, service.getPrecision(chatId)));
        answerCallbackQuery.setText("You selected price precision");
    }

    private void handleGetInformation(Long chatId, BotUserService service) throws IOException, TelegramApiException {
        String answerFromMenu = service.getInfo(chatId);
        getAnswerMessage(chatId, answerFromMenu);
        execute(new StartMenu(chatId).getMessage());
    }

    private void getAnswerMessage(Long chatId, String message) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(Long.toString(chatId));
        execute(sendMessage);
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
