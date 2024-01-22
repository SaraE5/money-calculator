package software.ulpgc.moneycalculator;

import java.util.List;

public class ExchangeMoneyCommand implements Command{
    private final MoneyDialog moneyDialog;
    private final CurrencyDialog currencyDialog;
    private final ExchangeRateLoader exchangeRateLoader;
    private final MoneyDisplay moneyDisplay;

    public ExchangeMoneyCommand(MoneyDialog moneyDialog, CurrencyDialog currencyDialog, ExchangeRateLoader exchangeRateLoader, MoneyDisplay moneyDisplay) {
        this.moneyDialog = moneyDialog;
        this.currencyDialog = currencyDialog;
        this.exchangeRateLoader = exchangeRateLoader;
        this.moneyDisplay = moneyDisplay;
    }

    @Override
    public void execute() {
        Money money = moneyDialog.get();
        Currency currency = currencyDialog.get();
        System.out.println(currency);
        List<ExchangeRate> exchangeRates = exchangeRateLoader.load(money.currency());
        ExchangeRate exchangeRate = exchangeRates.stream()
                .filter(e -> e.to().equals(currency))
                .findFirst()
                .orElse(null);
        Money result = new Money(money.amount() * exchangeRate.rate(), currency);

        moneyDisplay.show(result);

    }
}
