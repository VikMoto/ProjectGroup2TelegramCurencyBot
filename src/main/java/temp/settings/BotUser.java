package temp.settings;

import lombok.Data;
import temp.currency.dto.Bank;

@Data
public class BotUser {
    private final long id;
    private Bank bank;
    private boolean usd;
    private boolean eur;
    private boolean gbp;
    private int rounding;
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
        usd = true;
        eur = false;
        gbp = false;
        rounding = 2;
        scheduler = true;
        schedulerTime = 9;

    }

}