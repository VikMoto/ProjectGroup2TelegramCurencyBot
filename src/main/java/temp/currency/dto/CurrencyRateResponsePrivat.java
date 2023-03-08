package temp.currency.dto;

import java.math.BigDecimal;

public class CurrencyRateResponsePrivat implements CurrencyRateResponse {
    Currency ccy;
    Currency base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
