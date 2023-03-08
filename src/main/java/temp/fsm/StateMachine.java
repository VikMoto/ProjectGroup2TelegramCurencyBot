package temp.fsm;

public class StateMachine {

    public void handle(String text) {
        if (text.equals("price precision")) {
            onSwitchedToChooseNumberDecimalPlaces();
            return;
        }
        if (text.equals("bank")) {
            onSwitchedToChooseBank();
            return;
        }

        if (text.equals("currencies")) {
            onSwitchedToChooseCurrencies();
            return;
        }
        if (text.equals("time")) {
            onSwitchedToTimeNotification();
            return;
        }

    }

    private void onSwitchedToChooseNumberDecimalPlaces() {
        //todo set pricePrecision  at
        // SettingsVariables.getInstance().setPricePrecision();
        System.out.println("todo set number of decimal places");

    }

    private void onSwitchedToChooseBank() {
        //todo set bank at
        // SettingsVariables.getInstance().setBanks();
        System.out.println("todo set bank");
    }

    private void onSwitchedToChooseCurrencies() {
        //todo set currencies at
        //SettingsVariables.getInstance().setCurrencies();
        System.out.println("todo set currencies");
    }

    private void onSwitchedToTimeNotification() {
        //todo set time at
        //SettingsVariables.getInstance().setTime();
        System.out.println("todo set time");
    }


}
