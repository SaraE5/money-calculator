package software.ulpgc.moneycalculator.mocks;

import software.ulpgc.moneycalculator.*;
import software.ulpgc.moneycalculator.exchangeratesapi.ApiCurrencyLoader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Currency> currencies = new ApiCurrencyLoader().load();
        ExchangeMoneyCommand command = new ExchangeMoneyCommand(
                new MockMoneyDialog().define(currencies),
                new MockCurrencyDialog().define(currencies),
                new MockExchangeRateLoader(),
                new MockMoneyDisplay()
        );
        command.execute();
    }
}
