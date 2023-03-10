package temp.Api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import temp.currency.dto.Currency;
import temp.currency.dto.CurrencyRateResponseMono;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MonoCurrencyService implements CurrencyService {
    @Override
    public HashMap<String, BigDecimal> getRate(Currency currency) {
        final String monoURL = "https://api.monobank.ua/bank/currency";
        final String monoResponse;
        try {
            monoResponse = Jsoup
                    .connect(monoURL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can`t to connect MonoBank API");
        }

        //replace for enum Currency
        String monoResponseWithReplacedCurrencyToEnum = monoResponse
                .replace(":840", ":USD")
                .replace(":978", ":EUR")
                .replace(":980", ":UAH");

        Type collectionType = new TypeToken<List<CurrencyRateResponseMono>>() {
        }.getType();
        List<CurrencyRateResponseMono> currencyItemMonos = new Gson().fromJson(monoResponseWithReplacedCurrencyToEnum, collectionType);

        BigDecimal monoBuy = getCurrency(CurrencyRateResponseMono::getRateBuy, currencyItemMonos, currency);
        BigDecimal monoSell = getCurrency(CurrencyRateResponseMono::getRateSell, currencyItemMonos, currency);

        HashMap<String, BigDecimal> monoRate = new HashMap<>();
        monoRate.put("buy" + currency, monoBuy);
        monoRate.put("sell" + currency, monoSell);
        return monoRate;
    }

    private static BigDecimal getCurrency(Function<CurrencyRateResponseMono, BigDecimal> function, List<CurrencyRateResponseMono> currencyItemMonos,
                                          Currency currency) {

        final BigDecimal bigDecimal = currencyItemMonos.stream()
                .filter(it -> it.getCurrencyCodeA() == currency)
                .map(function)
                .collect(Collectors.toList()).get(0);
        return bigDecimal;
    }
}
