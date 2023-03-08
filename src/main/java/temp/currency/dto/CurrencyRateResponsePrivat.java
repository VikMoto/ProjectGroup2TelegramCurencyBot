package temp.currency.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
@Data
public class CurrencyRateResponsePrivat implements CurrencyRateResponse {
    Currency ccy;
    Currency base_ccy;
    @Getter
    private BigDecimal buy;
    @Getter
    private BigDecimal sale;

}
