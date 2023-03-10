package temp;

import lombok.extern.slf4j.Slf4j;
import temp.Api.CurrencyService;
import temp.Api.MonoCurrencyService;
import temp.Api.NBUCurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Currency;
import temp.telegram.TelegramBotService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Slf4j
public class TelegramBotApp {
    public static void main(String[] args) throws IOException {
        TelegramBotService botService = new TelegramBotService();
        log.info("API starts working");
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

        System.out.println("Privat rate = " + rate);
        System.out.println("Monobank rate = " + rateMono);
        System.out.println("NBU rate = " + rateNBU);
    }
}
