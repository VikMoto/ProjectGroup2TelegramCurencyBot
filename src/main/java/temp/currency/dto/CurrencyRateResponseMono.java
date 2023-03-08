package temp.currency.dto;

import java.math.BigDecimal;

public class CurrencyRateResponseMono implements CurrencyRateResponse {
    private int currencyCodeA;
    private int currencyCodeB;
    private long date;
    private BigDecimal rateBuy;
    private BigDecimal rateSell;
}
