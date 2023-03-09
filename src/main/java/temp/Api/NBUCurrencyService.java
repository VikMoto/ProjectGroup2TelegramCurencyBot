package temp.Api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import temp.currency.dto.Currency;
import temp.currency.dto.CurrencyRateResponseNBU;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NBUCurrencyService implements CurrencyService {

    @Override
    public HashMap<String, BigDecimal> getRate(Currency currency) {
        final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        final String NBU_RESPONSE;
        try {
            NBU_RESPONSE = Jsoup.connect(NBU_URL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can`t to connect NBU API");
        }
        Type collectionType = new TypeToken<List<CurrencyRateResponseNBU>>() {}.getType();
        List<CurrencyRateResponseNBU> currencyItemNBUs = new Gson().fromJson(NBU_RESPONSE, collectionType);

        BigDecimal buy = getCurrency(CurrencyRateResponseNBU::getRate, currencyItemNBUs, currency);
        BigDecimal sell = buy.multiply(BigDecimal.valueOf(0.95));

        HashMap<String, BigDecimal> nbuRate = new HashMap<>();
        nbuRate.put("buy" + currency, buy);
        nbuRate.put("sell" + currency, sell);

        return nbuRate;
    }

    private static BigDecimal getCurrency(Function<CurrencyRateResponseNBU, BigDecimal> function, List<CurrencyRateResponseNBU> currencyItemNBUs,
                                          Currency currency) {
        return currencyItemNBUs.stream()
                .filter(it -> it.getCc() == currency)
                .map(function)
                .collect(Collectors.toList()).get(0);
    }

}
