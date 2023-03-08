package temp.currency;

import temp.currency.dto.Currency;
import java.io.IOException;
import java.util.HashMap;

public interface CurrencyService {
    HashMap<String, Double> getRate(Currency currency) throws IOException;
}
