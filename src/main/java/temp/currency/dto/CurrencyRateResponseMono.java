package temp.currency.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyRateResponseMono implements CurrencyRateResponse {
    private Currency currencyCodeA;
    private Currency currencyCodeB;
    private long date;
    private BigDecimal rateBuy;
    private BigDecimal rateSell;
}
