package temp.Api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import temp.currency.CurrencyService;
import temp.currency.dto.Currency;
import temp.currency.dto.CurrencyItem;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrivatBankCurrencyService implements CurrencyService {

    @Override
    public HashMap<String, Double> getRate(Currency currency) {
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

        Type collectionType = new TypeToken<List<CurrencyItem>>() {
        }.getType();
        List<CurrencyItem> currencyItemPrivats = new Gson().fromJson(privatResponse, collectionType);

        double privatBuy = getCurrency(CurrencyItem::getBuy, currencyItemPrivats, currency);
        double privatSell = getCurrency(CurrencyItem::getSale, currencyItemPrivats, currency);

        HashMap<String, Double> privatRate = new HashMap<>();
        privatRate.put("buy" + currency, privatBuy);
        privatRate.put("sell" + currency, privatSell);

        return privatRate;
    }

    private static double getCurrency(Function<CurrencyItem, Float> function, List<CurrencyItem> currencyItemPrivats,
                                      Currency currency) {
        return currencyItemPrivats.stream()
                .filter(it -> it.getCcy() == currency)
                .map(function)
                .collect(Collectors.toList()).get(0);
    }
}
