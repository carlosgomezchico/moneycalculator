import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TsvFileCurrencyLoader currencyLoader = new TsvFileCurrencyLoader(new File("currencies.tsv"));
        List<Currency> currencies = currencyLoader.load();
        ExchangeRateLoader rateLoader = new ExchangeRateLoader();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce la cantidad de dinero: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Introduce el código de la divisa de origen: ");
        Currency from = findCurrency(currencies, scanner.nextLine());
        System.out.print("Introduce el código de la divisa de destino: ");
        Currency to = findCurrency(currencies, scanner.nextLine());

        ExchangeRate rate = rateLoader.getExchangeRate(from, to);
        Money convertedMoney = convert(new Money(amount, from), rate);

        System.out.println("Cantidad convertida: " + convertedMoney);
    }

    private static Currency findCurrency(List<Currency> currencies, String code) {
        return currencies.stream()
                .filter(currency -> currency.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Divisa no encontrada: " + code));
    }

    private static Money convert(Money original, ExchangeRate rate) {
        if (!original.getCurrency().equals(rate.getFrom())) {
            throw new IllegalArgumentException("Las divisas no coinciden");
        }
        double convertedAmount = original.getAmount() * rate.getRate();
        return new Money(convertedAmount, rate.getTo());
    }
}
