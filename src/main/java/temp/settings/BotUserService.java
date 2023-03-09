package temp.settings;

import temp.Api.MonoCurrencyService;
import temp.Api.NBUCurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.CurrencyService;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;
import temp.ui.PrettyPrintCurrencyServise;

import java.io.IOException;
import java.util.List;

public class BotUserService {
    private static volatile BotUserService instance;
    private StorageOfUsers userStorage;

    protected NBUCurrencyService nbuCurrencyService = new NBUCurrencyService();
    protected PrivatBankCurrencyService privatBankCurrencyService = new PrivatBankCurrencyService();
    protected MonoCurrencyService monoCurrencyService = new MonoCurrencyService();

    public BotUserService() {
        userStorage = StorageOfUsers.getInstance();
    }

    public static BotUserService getInstance() { //«блокировка с двойной проверкой» (Double-Checked Locking)
        BotUserService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (StorageOfUsers.class) {
            if (instance == null) {
                instance = new BotUserService();
            }
            return instance;
        }
    }

    public void createUser(long userId) {
        userStorage.add(new BotUser(userId));
    }

    public void setBank(long userId, Bank bank) {
        userStorage.get(userId).setBank(bank);
    }

    public Bank getBank(long userId) {
        return userStorage.get(userId).getBank();
    }

    public void setPrecision(long userId, int precision) {
        userStorage.get(userId).setPrecision(precision);
    }
    public void setCurrencies(long userId, Currency currency) {
        userStorage.get(userId).setCurrency(currency);
    }
    public Currency getCurrency(long userId) {
       return userStorage.get(userId).getCurrency();
    }









    public int getPrecision(long userId) {
        return userStorage.get(userId).getPrecision();
    }


    public boolean getScheduler(long userId) {
        return userStorage.get(userId).isScheduler();
    }

    public int getSchedulerTime(long userId) {
        return userStorage.get(userId).getSchedulerTime();
    }

    public void setScheduler(long userId, boolean scheduler) {
        userStorage.get(userId).setScheduler(scheduler);
    }

    public void setSchedulerTime(long userId, int time) {
        userStorage.get(userId).setSchedulerTime(time);
    }





    public List<Long> getUsersWithNotificationOnCurrentHour(int time) {
        return userStorage.getUsersWithNotficationOnCurrentHour(time);
    }


    public String getInfo(long userId) throws IOException {
        Bank bank = getBank(userId);
//        Bank bank = Bank.PrivatBank;

        int precision = getPrecision(userId);
        Currency currency = getCurrency(userId);
        String result = "";
        String currencyPairUsd = "UAH/USD";
        String currencyPairEur = "UAH/EUR";

        if (bank == Bank.NBU) {


        }

        if (bank == Bank.MonoBank) {


        }

        if (bank == Bank.PrivatBank) {

                  CurrencyService currencyService = new PrivatBankCurrencyService();
                  result = bank.name() + "\n"  + new PrettyPrintCurrencyServise().convert(currencyService.getRate(currency),currency, getPrecision(userId));

        }
//        log.info(result);
        return result;
    }


}
