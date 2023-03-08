package temp;

import temp.currency.CurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Currency;
import temp.telegram.TelegramBotService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class TelegramBotApp {
    public static void main(String[] args) throws IOException {
        TelegramBotService botService = new TelegramBotService();


        CurrencyService currencyService = new PrivatBankCurrencyService();
        Currency currency = Currency.USD;
        Map<String, BigDecimal> rate = currencyService.getRate(currency);
       // String convert = new PrettyPrintCurrencyServise().convert(rate, currency, 3);

        System.out.println(rate);
    }
}
