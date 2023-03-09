package temp.Api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import temp.currency.dto.Currency;
import temp.currency.dto.CurrencyRateResponsePrivat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrivatBankCurrencyService implements CurrencyService {

    @Override
    public HashMap<String, BigDecimal> getRate(Currency currency) {
        final String privatURL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        final String privatResponse;
        try {
            privatResponse = Jsoup
                    .connect(privatURL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can`t to connect Privat API");
        }

        Type collectionType = new TypeToken<List<CurrencyRateResponsePrivat>>() {
        }.getType();
        List<CurrencyRateResponsePrivat> currencyItemPrivats = new Gson().fromJson(privatResponse, collectionType);

        BigDecimal privatBuy = getCurrency(CurrencyRateResponsePrivat::getBuy, currencyItemPrivats, currency);
        BigDecimal privatSell = getCurrency(CurrencyRateResponsePrivat::getSale, currencyItemPrivats, currency);

        HashMap<String, BigDecimal> privatRate = new HashMap<>();
        privatRate.put("buy" + currency, privatBuy);
        privatRate.put("sell" + currency, privatSell);

        return privatRate;
    }

    private static BigDecimal getCurrency(Function<CurrencyRateResponsePrivat, BigDecimal> function, List<CurrencyRateResponsePrivat> currencyItemPrivats,
                                      Currency currency) {
        final BigDecimal bigDecimal = currencyItemPrivats.stream()
                .filter(it -> it.getCcy() == currency)
                .map(function)
                .collect(Collectors.toList()).get(0);
        return bigDecimal;
    }
}
