package temp.currency;

import lombok.Data;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SettingsVariables {

    private int pricePrecision;
    private List<Bank> banks;
    private List<Currency> currencies;
    LocalDateTime time;

    private static SettingsVariables instance;

    private SettingsVariables() {
    }
}
