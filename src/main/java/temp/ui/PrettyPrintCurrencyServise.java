package temp.ui;

import temp.currency.dto.Currency;

import java.math.BigDecimal;
import java.util.HashMap;

public class PrettyPrintCurrencyServise {
    public String convert(HashMap<String, BigDecimal> rate, Currency currency, int precision) {
        String template = "UAH / {currency}: buy {buy} - sell {sell}";

        BigDecimal buyRate = rate.get("buy" + currency);
        BigDecimal sellRate = rate.get("sell" + currency);

        return template
                .replace("{currency}", currency.name())
                .replace("{buy}", String.format("%." + precision + "f", buyRate.doubleValue()) + "")
                .replace("{sell}", String.format("%." + precision + "f", sellRate.doubleValue()) + "");
    }
}
