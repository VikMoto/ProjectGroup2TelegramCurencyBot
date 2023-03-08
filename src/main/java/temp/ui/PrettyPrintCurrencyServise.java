package temp.ui;

import temp.currency.dto.Currency;

import java.math.BigDecimal;
import java.util.HashMap;

public class PrettyPrintCurrencyServise {
    public String convert(HashMap<String, BigDecimal> rate, Currency currency, int precision) {
        String template = "Exchange Rate \n UAH => 1 {currency}: buy {buy} - sell {sell}";

        BigDecimal buyRate = rate.get("buyUSD");
        BigDecimal sellRate = rate.get("sellUSD");

        return template
                .replace("{currency}", currency.name())
                .replace("{buy}", String.format("%." + precision + "f", buyRate.doubleValue()) + "")
                .replace("{sell}", String.format("%." + precision + "f", sellRate.doubleValue()) + "");
    }
}
