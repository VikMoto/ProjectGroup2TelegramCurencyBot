package temp.telegram;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import temp.Api.CurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Bank;
import temp.settings.StorageOfUsers;
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
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    private static volatile CurrencyTelegramBot instance;
    private CurrencyService currencyService;
    private PrettyPrintCurrencyServise prettyPrintCurrencyServise;
    private BotUserService service = BotUserService.getInstance();

    private CurrencyTelegramBot() {
        prettyPrintCurrencyServise = new PrettyPrintCurrencyServise();

        register(new StartCommand());
        register(new HelpCommand());
    }

    public static CurrencyTelegramBot getInstance() { //«блокировка с двойной проверкой» (Double-Checked Locking)
        CurrencyTelegramBot result = instance;
        if (result != null) {
            return result;
        }
        synchronized (CurrencyTelegramBot.class) {
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


            handleMainMenu(callbackData, chatId, service, answerCallbackQuery);

            handlePricePrecision(service, callbackData, chatId);

            handleNotificationTime(service, callbackData, chatId);

            handleSetBank(service, callbackData, chatId);

            execute(answerCallbackQuery);

        }

    }

    private void handleMainMenu(String callbackData, Long chatId, BotUserService service, AnswerCallbackQuery answerCallbackQuery) throws IOException, TelegramApiException {
        switch (callbackData) {
            case "getInformation" -> handleGetInformation(chatId);
            case "settings" -> handleSettingsMainMenu(chatId, answerCallbackQuery);
            case "price precision" -> handlePricePrecision(chatId, answerCallbackQuery, service);
            case "bank" -> handleBankSelection(chatId, answerCallbackQuery, service);
            case "currency" -> answerCallbackQuery.setText("You chose currency");
            case "time notification" -> handleTimeNoticeMainManu(chatId, service);

        }
    }
    private void handleSetBank(BotUserService service, String callbackData, Long chatId) throws TelegramApiException {
        if (callbackData.contains("setBank")) {
            switch (callbackData) {
                case "setBankNBU" -> handleSetBankSelection(chatId, service, Bank.NBU);
                case "setBankMonoBank" -> handleSetBankSelection(chatId, service, Bank.MonoBank);
                case "setBankPrivat" -> handleSetBankSelection(chatId, service, Bank.PrivatBank);
            }
        }
    }


    private void handlePricePrecision(BotUserService service, String callbackData, Long chatId) throws TelegramApiException {
        if (callbackData.contains("pricePrecision")) {
            switch (callbackData) {
                case "pricePrecision2" -> handlePricePrecisionSelection(chatId, 2, service);
                case "pricePrecision3" -> handlePricePrecisionSelection(chatId, 3, service);
                case "pricePrecision4" -> handlePricePrecisionSelection(chatId, 4, service);
            }
        }
    }




    private void handleTimeNoticeMainManu(Long chatId, BotUserService service) throws TelegramApiException {
        NotificationsTime notificationsTime = new NotificationsTime(String.valueOf(service.getSchedulerTime(chatId)), chatId);
        execute(notificationsTime.getMessage());
    }

    private void handleNotificationTime(BotUserService service, String callbackData, Long chatId) throws TelegramApiException {
        if (callbackData.contains("noticeTime")){

            if(callbackData.equals("noticeTimeCancelNotifications")) {
                service.setScheduler(chatId, false);
//                    //todo remove service.getScheduler(chatId) after all test
                getAnswerMessage(chatId, "You Cancel Notifications " + service.getScheduler(chatId));
                execute(new StartMenu(chatId).getMessage());
            }

            List<String> noticeTimeCallbackData = List.of("noticeTime9", "noticeTime10", "noticeTime11",
                    "noticeTime12", "noticeTime13", "noticeTime14", "noticeTime15", "noticeTime16",
                    "noticeTime17", "noticeTime18");
            int index = noticeTimeCallbackData.indexOf(callbackData);
            if (index >= 0) {
                int hour = index + 9;
                handleSchedulerTimeSelection(chatId, hour, service);
            }
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

    public void handleGetInformation(Long chatId) throws IOException, TelegramApiException {
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
