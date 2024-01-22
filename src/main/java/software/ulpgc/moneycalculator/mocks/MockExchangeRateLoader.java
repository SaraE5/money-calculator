package software.ulpgc.moneycalculator.mocks;

import software.ulpgc.moneycalculator.Currency;
import software.ulpgc.moneycalculator.ExchangeRate;
import software.ulpgc.moneycalculator.ExchangeRateLoader;

import java.time.LocalDate;
import java.util.List;

public class MockExchangeRateLoader implements ExchangeRateLoader {
    @Override
    public List<ExchangeRate> load(Currency from) {
        return List.of(
                new ExchangeRate(from, new Currency("AFN", "Afghan Afghani"), LocalDate.now(), 0.32234)
        );
    }
}
