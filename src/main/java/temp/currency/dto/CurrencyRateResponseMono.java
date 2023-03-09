package temp.currency.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyRateResponseMono implements CurrencyRateResponse {
    private int currencyCodeA;
    private int currencyCodeB;
    private long date;
    private BigDecimal rateBuy;
    private BigDecimal rateSell;
}
