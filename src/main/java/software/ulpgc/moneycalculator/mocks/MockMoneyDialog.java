package software.ulpgc.moneycalculator.mocks;

import software.ulpgc.moneycalculator.Currency;
import software.ulpgc.moneycalculator.Money;
import software.ulpgc.moneycalculator.MoneyDialog;

import java.util.ArrayList;
import java.util.List;

public class MockMoneyDialog implements MoneyDialog {
    private final List<Currency> currencies;

    public MockMoneyDialog() {
        this.currencies = new ArrayList<>();
    }

    @Override
    public MoneyDialog define(List<Currency> currencies) {
        this.currencies.clear();
        this.currencies.addAll(currencies);
        return this;
    }

    @Override
    public Money get() {
        return new Money(200, currencies.get(0));
    }
}
