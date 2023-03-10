package temp.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateDto implements Comparable<CurrencyRateDto> {
    private Currency currency;
    private BigDecimal buyRate;
    private BigDecimal sellRate;

    @Override
    public int compareTo(CurrencyRateDto o) {
        return this.currency.compareTo(o.currency);
    }
}
