package software.ulpgc.moneycalculator.swing;

import software.ulpgc.moneycalculator.*;
import software.ulpgc.moneycalculator.exchangeratesapi.ApiCurrencyLoader;
import software.ulpgc.moneycalculator.mocks.MockExchangeRateLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MoneyCalculatorGUI extends JFrame {
    private JComboBox<Currency> fromCurrencyComboBox;
    private JComboBox<Currency> toCurrencyComboBox;
    private JTextField amountTextField;
    private JTextField resultTextField;

    private ExchangeRateLoader exchangeRateLoader;

    public MoneyCalculatorGUI(List<Currency> currencies, ExchangeRateLoader exchangeRateLoader) {
        this.exchangeRateLoader = exchangeRateLoader;
        initializeUI(currencies);
    }

    private void initializeUI(List<Currency> currencies) {
        setTitle("Money Calculator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel, currencies);

        setVisible(true);
    }

    private void placeComponents(JPanel panel, List<Currency> currencies) {
        panel.setLayout(null);

        JLabel fromCurrencyLabel = new JLabel("From Currency:");
        fromCurrencyLabel.setBounds(10, 20, 120, 25);
        panel.add(fromCurrencyLabel);

        fromCurrencyComboBox = new JComboBox<>(currencies.toArray(new Currency[0]));
        fromCurrencyComboBox.setBounds(140, 20, 120, 25);
        panel.add(fromCurrencyComboBox);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 50, 80, 25);
        panel.add(amountLabel);

        amountTextField = new JTextField(20);
        amountTextField.setBounds(140, 50, 120, 25);
        panel.add(amountTextField);

        JLabel toCurrencyLabel = new JLabel("To Currency:");
        toCurrencyLabel.setBounds(10, 80, 120, 25);
        panel.add(toCurrencyLabel);

        toCurrencyComboBox = new JComboBox<>(currencies.toArray(new Currency[0]));
        toCurrencyComboBox.setBounds(140, 80, 120, 25);
        panel.add(toCurrencyComboBox);

        JButton convertButton = new JButton("Convert");
        convertButton.setBounds(10, 110, 80, 25);
        panel.add(convertButton);

        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setBounds(10, 140, 80, 25);
        panel.add(resultLabel);

        resultTextField = new JTextField(20);
        resultTextField.setBounds(140, 140, 120, 25);
        resultTextField.setEditable(false);
        panel.add(resultTextField);

        // Action listener for the Convert button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertButtonClicked();
            }
        });
    }

    private void convertButtonClicked() {
        Currency fromCurrency = (Currency) fromCurrencyComboBox.getSelectedItem();
        Currency toCurrency = (Currency) toCurrencyComboBox.getSelectedItem();
        double amount = Double.parseDouble(amountTextField.getText());

        List<ExchangeRate> exchangeRates = exchangeRateLoader.load(fromCurrency);
        ExchangeRate exchangeRate = exchangeRates.stream()
                .filter(e -> e.to().equals(toCurrency))
                .findFirst()
                .orElse(null);

        double resultAmount = amount * exchangeRate.rate();
        resultTextField.setText(String.valueOf(resultAmount));
    }

    public static void main(String[] args) {
        List<Currency> currencies = new ApiCurrencyLoader().load();
        ExchangeRateLoader exchangeRateLoader = new MockExchangeRateLoader();

        SwingUtilities.invokeLater(() -> new MoneyCalculatorGUI(currencies, exchangeRateLoader));
    }
}
