package temp;

import temp.currency.CurrencyService;
import temp.currency.PrivatBankCurrencyService;
import temp.currency.dto.Currency;
import temp.telegram.TelegramBotService;
import temp.ui.PrettyPrintCurrencyServise;

import java.io.IOException;

public class TelegramBotApp {
    public static void main(String[] args) throws IOException {
        TelegramBotService botService = new TelegramBotService();


        CurrencyService currencyService = new PrivatBankCurrencyService();
        Currency currency = Currency.USD;
        double[] rate = currencyService.getRate(currency);
        String convert = new PrettyPrintCurrencyServise().convert(rate, currency);

        System.out.println(convert);
    }
}
