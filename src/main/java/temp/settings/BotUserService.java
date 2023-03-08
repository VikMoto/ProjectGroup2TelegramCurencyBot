package temp.settings;

import temp.Api.MonoCurrencyService;
import temp.Api.NBUCurrencyService;
import temp.Api.PrivateBankCurrencyService;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;

import java.text.MessageFormat;
import java.util.List;

public class BotUserService {
    private static volatile BotUserService instance;
    private StorageOfUsers userStorage;

    NBUCurrencyService nbuCurrencyService = new NBUCurrencyService();
    PrivateBankCurrencyService privateBankCurrencyService = new PrivateBankCurrencyService();
    MonoCurrencyService monoCurrencyService = new MonoCurrencyService();

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

    public void setBank(long userId, String bank) {
        userStorage.get(userId).setBank(Bank.valueOf(bank));
    }

    public Bank getBank(long userId) {
        return userStorage.get(userId).getBank();
    }

    public void setRounding(long userId, int rounding) {
        userStorage.get(userId).setRounding(rounding);
    }

    public void setUsd(long userId, boolean usd) {
        userStorage.get(userId).setUsd(usd);
    }

    public void setEur(long userId, boolean eur) {
        userStorage.get(userId).setEur(eur);
    }



    public int getRounding(long userId) {
        return userStorage.get(userId).getRounding();
    }

    public boolean getUsd(long userId) {
        return userStorage.get(userId).isUsd();
    }

    public boolean getEur(long userId) {
        return userStorage.get(userId).isEur();
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



    public String getCurrency(long userId) {
        if (getUsd(userId)) {
            return "usd";
        } else  {
            return "eur";
        }
    }

    public List<Long> getUsersWithNotificationOnCurrentHour(int time) {
        return userStorage.getUsersWithNotficationOnCurrentHour(time);
    }


    public String getInfo(long userId) {
        Bank bank = getBank(userId);
        boolean usd = getUsd(userId);
        boolean eur = getEur(userId);

        int rounding = getRounding(userId);
        String result = "";
        String currencyPairUsd = "UAH/USD";
        String currencyPairEur = "UAH/EUR";

        if (bank == Bank.NBU) {

//            if (getUsd(userId)) {
//                double purchaseRate1 = nbuCurrencyService.getRate(Currency.USD).get("rateUSD");
//                double purchaseRate = Precision.round(purchaseRate1, rounding);
//                result = MessageFormat
//                        .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "NBU", currencyPairUsd, String.format("%." + rounding + "f", purchaseRate));
//
//            }
//            if (getEur(userId)) {
//                double purchaseRate1 = nbuCurrencyService.getRate(Currency.EUR).get("rateEUR");
//                double purchaseRate = Precision.round(purchaseRate1, rounding);
//                result = MessageFormat
//                        .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "NBU", currencyPairEur, String.format("%." + rounding + "f", purchaseRate));
//
//            }

        }

        if (bank == Bank.MonoBank) {
            if (getUsd(userId)) {
//                double purchaseRate = monoCurrencyService.getRate(Currency.USD).get("buyUSD");
//                double saleRate = monoCurrencyService.getRate(Currency.USD).get("SellUSD");
//                if (saleRate == 0) {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "Monobank", currencyPairUsd, String.format("%." + rounding + "f", purchaseRate));
//                } else {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: {3}", "Monobank", currencyPairUsd, String.format("%." + rounding + "f", purchaseRate), String.format("%." + rounding + "f", saleRate));
//                }
            }
            if (getEur(userId)) {
//                double purchaseRate = monoCurrencyService.getRate(Currency.EUR).get("buyEUR");
//                double saleRate = monoCurrencyService.getRate(Currency.EUR).get("SellEUR");
//
//                if (saleRate == 0) {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "Monobank", currencyPairEur, String.format("%." + rounding + "f", purchaseRate));
//                } else {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: {3}", "Monobank", currencyPairEur, String.format("%." + rounding + "f", purchaseRate), String.format("%." + rounding + "f", saleRate));
//                }
            }
        }

        if (bank == Bank.PrivatBank) {
            if (getUsd(userId)) {
//                double purchaseRate = privateBankCurrencyService.getRate(Currency.USD).get("buyUSD");
//                double saleRate = privateBankCurrencyService.getRate(Currency.USD).get("sellUSD");
//                if (saleRate == 0) {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "Private", currencyPairUsd, String.format("%." + rounding + "f", purchaseRate));
//                } else {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: {3}", "Private", currencyPairUsd, String.format("%." + rounding + "f", purchaseRate), String.format("%." + rounding + "f", saleRate));
//                }
            }
            if (getEur(userId)) {
//                double purchaseRate = privateBankCurrencyService.getRate(Currency.EUR).get("sellEUR");
//                double saleRate = privateBankCurrencyService.getRate(Currency.EUR).get("buyEUR");
//                if (saleRate == 0) {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "Privat", currencyPairEur, String.format("%." + rounding + "f", saleRate));
//                } else {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: {3}", "Privat", currencyPairEur, String.format("%." + rounding + "f", saleRate), String.format("%." + rounding + "f", purchaseRate));
//                }
            }

        }
//        log.info(result);
        return result;
    }


}
