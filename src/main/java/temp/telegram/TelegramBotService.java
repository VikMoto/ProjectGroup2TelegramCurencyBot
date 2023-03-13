package temp.telegram;

import org.quartz.SchedulerException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import temp.schedule.ScheduleService;
import temp.schedule.impl.ScheduleServiceImpl;

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
