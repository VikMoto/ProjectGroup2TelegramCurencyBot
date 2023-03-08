package temp.currency.dto;

import java.math.BigDecimal;

public class CurrencyRateDto implements Comparable<CurrencyRateDto> {
    private Currency currency;
    private BigDecimal buyRate;
    private BigDecimal sellRate;

    @Override
    public int compareTo(CurrencyRateDto o) {
        return 0;
    }

}
