package temp.ui;

import temp.currency.dto.Currency;

public class  PrettyPrintCurrencyServise {
    public String convert(double[] rate , Currency currency) {
        String template = "Exchange Rate \n UAH => 1 {currency}: buy {buy} - sell {sell}";

        float roundedRateBuy = Math.round(rate[0] * 100d)/100f;
        float roundedRateSell = Math.round(rate[1] * 100d)/100f;

        return template
                .replace("{currency}", currency.name())
                .replace("{buy}", roundedRateBuy + "")
                .replace("{sell}", roundedRateSell + "");
    }
}
