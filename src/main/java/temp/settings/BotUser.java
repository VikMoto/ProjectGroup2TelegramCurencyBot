package temp.settings;

import lombok.Data;
import lombok.Getter;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;

import java.util.ArrayList;
import java.util.List;

@Data
public class BotUser {
    private final long id;
    private Bank bank;
    private Currency currency;
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
        currency = Currency.USD;
        precision = 2;
        scheduler = true;
        schedulerTime = 9;

    }

}