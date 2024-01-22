package software.ulpgc.moneycalculator.exchangeratesapi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.ulpgc.moneycalculator.Currency;
import software.ulpgc.moneycalculator.CurrencyLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ApiCurrencyLoader implements CurrencyLoader {
    private final static String API_KEY = "792044ac0ebf2863e3ffac5329bba589";
    private final static String Url = "http://api.exchangeratesapi.io/v1/symbols?access_key=:API_KEY:";
    @Override
    public List<Currency> load() {
        try {
            return load(readCurrencies());
        } catch (IOException e) {
            return emptyList();
        }
    }

    private List<Currency> load(String json) {
        return load(symbolsIn(json));
    }

    private List<Currency> load(Map<String, JsonElement> symbols) {
        return symbols.keySet().stream()
                .map(k -> toCurrency(k, symbols.get(k).getAsString()))
                .collect(toList());
    }

    private static Map<String, JsonElement> symbolsIn(String json) {
        return new Gson()
                .fromJson(json, JsonObject.class)
                .get("symbols")
                .getAsJsonObject()
                .asMap();
    }

    private Currency toCurrency(String code, String name) {
        return new Currency(code, name);
    }

    private String readCurrencies() throws IOException {
        try (InputStream is = new URL(Url.replace(":API_KEY:", API_KEY)).openStream()) {
            return new String(is.readAllBytes());
        }
    }
}
