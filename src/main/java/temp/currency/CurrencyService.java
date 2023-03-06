package temp.currency;


import temp.currency.dto.Currency;

import java.io.IOException;

public interface CurrencyService {
    double[] getRate(Currency currency) throws IOException;
    }
