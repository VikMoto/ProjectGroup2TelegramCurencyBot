package temp.settings;

import lombok.Data;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;

import java.util.ArrayList;
import java.util.List;

@Data
public class BotUser {
    private final long id;
    private Bank bank;
    private List<Currency> currencies = new ArrayList<>();
    private int precision;
    private boolean scheduler;
    private int schedulerTime;

    public int getSchedulerTime() {
        return schedulerTime;
    }

    public void setSchedulerTime(int schedulerTime) {
        this.schedulerTime = schedulerTime;
    }

    public BotUser(long id) {
        this.id = id;
        bank = Bank.NBU;
        currencies.add(Currency.USD);
        currencies.add(Currency.EUR);
        precision = 2;
        scheduler = true;
        schedulerTime = 9;
    }
}