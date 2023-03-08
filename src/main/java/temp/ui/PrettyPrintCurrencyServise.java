package temp.ui;

import temp.currency.dto.Currency;

import java.util.HashMap;

public class PrettyPrintCurrencyServise {
    public String convert(HashMap<String, Double> rate, Currency currency, int precision) {
        String template = "Exchange Rate \n UAH => 1 {currency}: buy {buy} - sell {sell}";

        double buyRate = rate.get("buyUSD");
        double sellRate = rate.get("sellUSD");

        return template
                .replace("{currency}", currency.name())
                .replace("{buy}", String.format("%." + precision + "f", buyRate) + "")
                .replace("{sell}", String.format("%." + precision + "f", sellRate) + "");
    }
}
