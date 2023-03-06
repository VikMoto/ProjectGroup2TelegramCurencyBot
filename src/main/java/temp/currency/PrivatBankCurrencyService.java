package temp.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import temp.currency.dto.Currency;
import temp.currency.dto.CurrencyItem;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrivatBankCurrencyService implements CurrencyService{
    Currency currency;
    Gson gson = new Gson();


    @Override
    public double[] getRate(Currency currency) {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        final String response;
        try {
            response = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can`t to connect Privat API");
        }


/** getParameterized getType*/
//        final TypeToken<?> typeToken = TypeToken
//                .getParameterized(List.class, CurrencyItem.class);
//        List<CurrencyItem> result = (List<CurrencyItem>) gson.fromJson(response, typeToken);
//        for (CurrencyItem item : result) {
//            System.out.println("item = " + item);
//        }

/** one more  getType and convert Json => Java Object */
        List<CurrencyItem> currencyItems = new ArrayList<>();
        Type collectionType = new TypeToken<List<CurrencyItem>>(){}.getType();
//   or such     Type type1 = com.google.gson.reflect.TypeToken.getParameterized(List.class, CurrencyItem.class).getType();
        List<CurrencyItem> enums = gson.fromJson(response, collectionType);

/** find USD/UAH */
        double[] aFloat = new double[]{0.0, 0.0};
        aFloat[0] = enums
                .stream()
                .filter(currencyItem -> currencyItem.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == currency.UAH)
                .map(it -> it.getBuy())
                .findFirst()
                .orElseThrow();

        aFloat[1] = enums
                .stream()
                .filter(currencyItem -> currencyItem.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == currency.UAH)
                .map(it -> it.getSale())
                .findFirst()
                .orElseThrow();


        System.out.println("USD/UAH " + aFloat[0] + " - " + aFloat[1]);

        return aFloat;
    }
}
