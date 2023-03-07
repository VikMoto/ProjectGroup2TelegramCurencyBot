package temp.telegram;

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
            String callbackQuery = update.getCallbackQuery().getData();
            Currency currencyQuery = Currency.valueOf(callbackQuery);


            String convertText = prettyPrintCurrencyServise.convert(
                    currencyService.getRate(currencyQuery), currencyQuery);

            SendMessage responseMessage = new SendMessage();
            responseMessage.setText(convertText);
            //
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            responseMessage.setChatId(Long.toString(chatId));
            execute(responseMessage); //@SneakyThrows instead try/Catch block
            System.out.println("callbackQuery = " + callbackQuery);
        }
        if (update.hasMessage()){
            String message = update.getMessage().getText();
            String responseText = "You wrote - " + message;

            SendMessage messageText = new SendMessage();
            messageText.setText(responseText);
            messageText.setChatId(Long.toString(update.getMessage().getChatId()));
            execute(messageText);//@SneakyThrows instead try/Catch block

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
