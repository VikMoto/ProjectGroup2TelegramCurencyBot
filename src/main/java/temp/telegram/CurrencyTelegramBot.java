package temp.telegram;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import temp.Api.CurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;
import temp.settings.BotUserService;
import temp.settings.StorageOfUsers;
import temp.settings.menu.*;
import temp.telegram.command.HelpCommand;
import temp.telegram.command.StartCommand;
import temp.ui.PrettyPrintCurrencyServise;

import java.io.IOException;
import java.util.List;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private static volatile CurrencyTelegramBot instance;
    private CurrencyService currencyService;
    private PrettyPrintCurrencyServise prettyPrintCurrencyServise;
    private  BotUserService service = BotUserService.getInstance();
    private CurrencyTelegramBot() {
        currencyService = new PrivatBankCurrencyService();
        prettyPrintCurrencyServise = new PrettyPrintCurrencyServise();

        register(new StartCommand());
        register(new HelpCommand());
    }

    public static CurrencyTelegramBot getInstance() { //«блокировка с двойной проверкой» (Double-Checked Locking)
        CurrencyTelegramBot result = instance;
        if (result != null) {
            return result;
        }
        synchronized (StorageOfUsers.class) {
            if (instance == null) {
                instance = new CurrencyTelegramBot();
            }
            return instance;
        }
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
        StartCommand startCommand = new StartCommand();

        if (update.hasCallbackQuery()) {

            CallbackQuery callbackQuery = update.getCallbackQuery();
            System.out.println("callbackQuery = " + callbackQuery.getData());

            CallbackQuery query = update.getCallbackQuery();
            String callbackData = query.getData();
            Long chatId = query.getMessage().getChatId();

            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();

            answerCallbackQuery.setCallbackQueryId(query.getId());


            handleMainMenu(callbackData, chatId,  answerCallbackQuery);

            handlePricePrecision( callbackData, chatId);

            handleNotificationTime( callbackData, chatId);

            handleSetBank( callbackData, chatId);
//            handleCurrencySelection(callbackData, chatId);
            handleCurrencySet(callbackData, chatId);

            execute(answerCallbackQuery);

        }

    }

    private void handleSetBank(String callbackData, Long chatId) throws TelegramApiException {
        if (callbackData.contains("setBank")) {
            switch (callbackData) {
                case "setBankNBU" -> handleSetBankSelection(chatId, Bank.NBU);
                case "setBankMonoBank" -> handleSetBankSelection(chatId, Bank.MonoBank);
                case "setBankPrivat" -> handleSetBankSelection(chatId, Bank.PrivatBank);
            }
        }
    }
    private void handleNotificationTime(String callbackData, Long chatId) throws TelegramApiException {
        if (callbackData.contains("noticeTime")){

            if(callbackData.equals("noticeTimeCancelNotifications")) {
                service.setScheduler(chatId, false);
//                    //todo remove service.getScheduler(chatId) after all test
                    getAnswerMessage(chatId, "You Cancel Notifications " + service.getScheduler(chatId));
                    execute(new StartMenu(chatId).getMessage());
            }

            List<String> noticeTimeCallbackData = List.of("noticeTime9", "noticeTime10", "noticeTime11",
                    "noticeTime12", "noticeTime13", "noticeTime14", "noticeTime15", "noticeTime16",
                    "noticeTime17", "noticeTime18", "noticeTime19", "noticeTime20");
            int index = noticeTimeCallbackData.indexOf(callbackData);
            if (index >= 0) {
                int hour = index + 9;
                handleSchedulerTimeSelection(chatId, hour);
            }
        }
    }

    private void handlePricePrecision(String callbackData, Long chatId) throws TelegramApiException {
        if (callbackData.contains("pricePrecision")) {
            switch (callbackData) {
                case "pricePrecision2" -> handlePricePrecisionSelection(chatId, 2);
                case "pricePrecision3" -> handlePricePrecisionSelection(chatId, 3);
                case "pricePrecision4" -> handlePricePrecisionSelection(chatId, 4);
            }
        }
    }

    private void handleCurrencySet(String callbackData, Long chatId) throws TelegramApiException {
        if (callbackData.contains("setCurrency")) {
            switch (callbackData) {
                case "setCurrencyUSD" -> handleCurrencySET(chatId, Currency.USD);
                case "setCurrencyEUR" -> handleCurrencySET(chatId, Currency.EUR);
            }
        }
    }

    private void handleCurrencySET(Long chatId, Currency currency) {

    }


    private void handleMainMenu(String callbackData, Long chatId, AnswerCallbackQuery answerCallbackQuery) throws IOException, TelegramApiException {
        switch (callbackData) {
            case "getInformation" -> handleGetInformation(chatId);
            case "settings" -> handleSettingsMainMenu(chatId, answerCallbackQuery);
            case "price precision" -> handlePricePrecision(chatId, answerCallbackQuery);
            case "bank" -> handleBankSelection(chatId, answerCallbackQuery);
            case "currencies" -> handleCurrencySelection(chatId,answerCallbackQuery);
            case "time notification" -> handleTimeNoticeMainManu(chatId);

        }
    }

    private void handleTimeNoticeMainManu(Long chatId) throws TelegramApiException {
        NotificationsTime notificationsTime = new NotificationsTime(String.valueOf(service.getSchedulerTime(chatId)), chatId);
        execute(notificationsTime.getMessage());
    }

    private void handleSchedulerTimeSelection(Long chatId, int time) throws TelegramApiException {
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

    private void handleBankSelection(Long chatId, AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException {
        BankMenu bank = new BankMenu(service.getBank(chatId).name(), chatId);
        execute(bank.getMessage(chatId));
        answerCallbackQuery.setText("You chose bank");
    }

    private void handleSetBankSelection(Long chatId, Bank bank) throws TelegramApiException {
        service.setBank(chatId,bank);
        getAnswerMessage(chatId, "You set Bank as " + service.getBank(chatId).name());
        execute(new StartMenu(chatId).getMessage());
    }

    private void handlePricePrecisionSelection(Long chatId, int precision) throws TelegramApiException {
        service.setPrecision(chatId, precision);
        getAnswerMessage(chatId, "Number of decimal places: " + service.getPrecision(chatId));
        execute(new StartMenu(chatId).getMessage());
    }

    private void handlePricePrecision(Long chatId, AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException {
        PricePrecisionMenu pricePrecisionMenu = new PricePrecisionMenu();
        execute(pricePrecisionMenu.getMessage(chatId, service.getPrecision(chatId)));
        answerCallbackQuery.setText("You selected price precision");
    }

    private void handleCurrencySelection(Long chatId,AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException{
        CurrencyMenu currency = new CurrencyMenu(service.getCurrency(chatId).name(),chatId);
        execute(currency.getMessage());
        answerCallbackQuery.setText("You chose currency");
    }

    public void handleGetInformation(Long chatId) throws IOException, TelegramApiException {
        String answerFromMenu = service.getInfo(chatId) + "\n" +
                service.getUsersWithNotificationOnCurrentHour(20);
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
