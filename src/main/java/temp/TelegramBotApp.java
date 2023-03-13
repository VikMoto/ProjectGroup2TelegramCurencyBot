package temp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import temp.Api.CurrencyService;
import temp.Api.MonoCurrencyService;
import temp.Api.NBUCurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Currency;
import temp.schedule.ScheduleService;
import temp.schedule.impl.ScheduleServiceImpl;
import temp.telegram.TelegramBotService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;



public class TelegramBotApp {

    private static final Logger logger = LogManager.getLogger(TelegramBotApp.class);
    public static void main(String[] args) throws IOException {
        TelegramBotService botService = new TelegramBotService();

        ScheduleService scheduleService = new ScheduleServiceImpl();
        try {
            scheduleService.init();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        //Privat bank test
        CurrencyService currencyService = new PrivatBankCurrencyService();
        Currency currency = Currency.USD;
        Map<String, BigDecimal> rate = currencyService.getRate(currency);

        //Monobank test
        CurrencyService currencyServiceMono = new MonoCurrencyService();
        Currency currencyMono = Currency.USD;
        Map<String, BigDecimal> rateMono = currencyServiceMono.getRate(currencyMono);

        //NBU test
        CurrencyService currencyServiceNBU = new NBUCurrencyService();
        Currency currencyNBU = Currency.USD;
        Map<String, BigDecimal> rateNBU = currencyServiceNBU.getRate(currencyNBU);


        logger.info("Privat rate = {}", rate);
        logger.info("Monobank rate = {}", rateMono);
        logger.info("NBU rate = {}", rateNBU);
    }
}
