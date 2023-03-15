package temp.settings;

import temp.Api.CurrencyService;
import temp.Api.MonoCurrencyService;
import temp.Api.NBUCurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;
import temp.ui.PrettyPrintCurrencyServise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BotUserService {
    private static volatile BotUserService instance;
    private final StorageOfUsers userStorage;

    public BotUserService() {
        userStorage = StorageOfUsers.getInstance();
    }

    public static BotUserService getInstance() {
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
        List<Currency> currencies = getCurrencies(userId);
        List<Currency> currenciesCopy = new ArrayList<>(currencies);
        if (currencies.contains(currency)) {
            currenciesCopy.remove(currency);
        } else {
            currenciesCopy.add(currency);
        }
        userStorage.get(userId).setCurrencies(currenciesCopy);
    }

    public List<Currency> getCurrencies(long userId) {
        return userStorage.get(userId).getCurrencies();
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
        System.out.println("bank = " + bank.name());

        List<Currency> currencies = getCurrencies(userId);
        String result = "";

        if (bank == Bank.NBU) {
            CurrencyService currencyService = new NBUCurrencyService();
            for (Currency currency : currencies) {
                result = result + bank.name() + "\n" + new PrettyPrintCurrencyServise().convert(currencyService.getRate(currency), currency, getPrecision(userId)) + "\n";
            }
        }

        if (bank == Bank.MonoBank) {
            CurrencyService currencyService = new MonoCurrencyService();
            for (Currency currency : currencies) {
                result = result + bank.name() + "\n" + new PrettyPrintCurrencyServise().convert(currencyService.getRate(currency), currency, getPrecision(userId)) + "\n";
            }
        }

        if (bank == Bank.PrivatBank) {
            CurrencyService currencyService = new PrivatBankCurrencyService();
            for (Currency currency : currencies) {
                result = result + bank.name() + "\n" + new PrettyPrintCurrencyServise().convert(currencyService.getRate(currency), currency, getPrecision(userId)) + "\n";
            }
        }
        return result;
    }
}
