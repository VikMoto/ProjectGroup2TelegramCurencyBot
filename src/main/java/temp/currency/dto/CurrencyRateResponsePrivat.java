package temp.currency.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyRateResponsePrivat implements CurrencyRateResponse {
    Currency ccy;
    Currency base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;

}
