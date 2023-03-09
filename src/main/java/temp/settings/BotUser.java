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
    @Getter
    private List<Currency> currencies;
    private int precision;
    private boolean scheduler;
    private int schedulerTime;

    //TODO currency field is missing, need to be ENUM or CONSTANT


    public int getSchedulerTime() {
        return schedulerTime;
    }

    public void setSchedulerTime(int schedulerTime) {
        this.schedulerTime = schedulerTime;
    }

    public BotUser(long id) {
        this.id = id;
        bank = Bank.NBU;
        currencies = new ArrayList<>();
        precision = 2;
        scheduler = true;
        schedulerTime = 9;

    }

}