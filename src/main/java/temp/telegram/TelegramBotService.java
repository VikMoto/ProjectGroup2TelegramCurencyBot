package temp.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotService {
    private CurrencyTelegramBot currencyTelegramBot;

    public TelegramBotService() {
        currencyTelegramBot = CurrencyTelegramBot.getInstance();

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTelegramBot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
