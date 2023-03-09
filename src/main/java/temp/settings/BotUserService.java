package temp.settings;

import temp.Api.MonoCurrencyService;
import temp.Api.NBUCurrencyService;
import temp.Api.PrivatBankCurrencyService;
import temp.currency.CurrencyService;
import temp.currency.dto.Bank;
import temp.currency.dto.Currency;
import temp.ui.PrettyPrintCurrencyServise;

import java.io.IOException;
import java.math.BigDecimal;
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
    public void setCurrencies(long userId, List<Currency> currencies) {
        userStorage.get(userId).setCurrencies(currencies);
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
//        Bank bank = Bank.PrivatBank;

        int precision = getPrecision(userId);
        String result = "";
        String currencyPairUsd = "UAH/USD";
        String currencyPairEur = "UAH/EUR";

        if (bank == Bank.NBU) {

//            if (getCurrencies(userId).contains(Currency.USD)) {
//                double purchaseRate1 = nbuCurrencyService.getRate(Currency.USD).get("rateUSD");
//                double purchaseRate = Precision.round(purchaseRate1, precision);
//                result = MessageFormat
//                        .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "NBU", currencyPairUsd, String.format("%." + precision + "f", purchaseRate));
//
//            }
//            if (getCurrencies(userId).contains(Currency.EUR)) {
//                double purchaseRate1 = nbuCurrencyService.getRate(Currency.EUR).get("rateEUR");
//                double purchaseRate = Precision.round(purchaseRate1, precision);
//                result = MessageFormat
//                        .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "NBU", currencyPairEur, String.format("%." + precision + "f", purchaseRate));
//
//            }

        }

        if (bank == Bank.MonoBank) {
            if (getCurrencies(userId).contains(Currency.USD)) {
//                double purchaseRate = monoCurrencyService.getRate(Currency.USD).get("buyUSD");
//                double saleRate = monoCurrencyService.getRate(Currency.USD).get("SellUSD");
//                if (saleRate == 0) {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "Monobank", currencyPairUsd, String.format("%." + precision + "f", purchaseRate));
//                } else {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: {3}", "Monobank", currencyPairUsd, String.format("%." + precision + "f", purchaseRate), String.format("%." + precision + "f", saleRate));
//                }
            }
            if (getCurrencies(userId).contains(Currency.EUR)) {
//                double purchaseRate = monoCurrencyService.getRate(Currency.EUR).get("buyEUR");
//                double saleRate = monoCurrencyService.getRate(Currency.EUR).get("SellEUR");
//
//                if (saleRate == 0) {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: ⏳ ", "Monobank", currencyPairEur, String.format("%." + precision + "f", purchaseRate));
//                } else {
//                    result = MessageFormat
//                            .format("{0} exchange rate: {1}\n Purchase: {2}\n Sale: {3}", "Monobank", currencyPairEur, String.format("%." + precision + "f", purchaseRate), String.format("%." + precision + "f", saleRate));
//                }
            }
        }

        if (bank == Bank.PrivatBank) {
//            if (getCurrencies(userId).contains(Currency.USD)) {
//                  CurrencyService currencyService = new PrivatBankCurrencyService();
//                  result = bank.name() + "\n"  + new PrettyPrintCurrencyServise().convert(currencyService.getRate(Currency.USD), Currency.USD, getPrecision(userId));
//
//            }
            System.out.println("getCurrencies(userId).contains(Currency.EUR) = " + getCurrencies(userId).contains(Currency.EUR));
            if (getCurrencies(userId).contains(Currency.EUR)) {
                CurrencyService currencyService = new PrivatBankCurrencyService();
                result =  "\n"  + new PrettyPrintCurrencyServise().convert(currencyService.getRate(Currency.EUR), Currency.EUR, getPrecision(userId));
                System.out.println("result = " + result);

            }

        }
//        log.info(result);
        return result;
    }


}
