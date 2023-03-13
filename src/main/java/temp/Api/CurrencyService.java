package temp.Api;

import temp.currency.dto.Currency;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface CurrencyService {
    HashMap<String, BigDecimal> getRate(Currency currency) throws IOException;
}
