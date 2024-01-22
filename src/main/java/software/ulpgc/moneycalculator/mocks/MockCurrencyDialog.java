package software.ulpgc.moneycalculator.mocks;

import software.ulpgc.moneycalculator.Currency;
import software.ulpgc.moneycalculator.CurrencyDialog;

import java.util.ArrayList;
import java.util.List;

public class MockCurrencyDialog implements CurrencyDialog {
    private final List<Currency> currencies;

    public MockCurrencyDialog() {
        this.currencies = new ArrayList<>();
    }

    @Override
    public CurrencyDialog define(List<Currency> currencies) {
        this.currencies.clear();
        this.currencies.addAll(currencies);
        return this;
    }

    @Override
    public Currency get() {
        return currencies.get(1);
    }
}
