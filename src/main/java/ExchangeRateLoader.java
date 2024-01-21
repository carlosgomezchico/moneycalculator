package PACKAGE_NAME;
public class ExchangeRateLoader {
    private final Map<String, Double> rates = new HashMap<>();

    public ExchangeRateLoader() {
        // Tasas de cambio ficticias
        rates.put("EURUSD", 1.1);
        rates.put("USDEUR", 0.9);
        // Añadir más tasas de cambio según sea necesario
    }

    public ExchangeRate getExchangeRate(Currency from, Currency to) {
        String key = from.getCode() + to.getCode();
        Double rate = rates.get(key);
        if (rate == null) {
            throw new IllegalArgumentException("Tasa de cambio no disponible para: " + key);
        }
        return new ExchangeRate(from, to, LocalDate.now(), rate);
    }
}

