package PACKAGE_NAME;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Cargar las divisas
        TsvFileCurrencyLoader loader = new TsvFileCurrencyLoader(new File("currencies.tsv"));
        List<Currency> currencies = loader.load();

        // Mostrar las divisas disponibles
        System.out.println("Divisas disponibles:");
        for (Currency currency : currencies) {
            System.out.println(currency);
        }

        // Obtener entrada del usuario para la conversión
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce la cantidad de dinero: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Introduce el código de la divisa de origen: ");
        String fromCode = scanner.nextLine();
        System.out.print("Introduce el código de la divisa de destino: ");
        String toCode = scanner.nextLine();

        // Encuentra las divisas en la lista cargada
        Currency fromCurrency = findCurrency(currencies, fromCode);
        Currency toCurrency = findCurrency(currencies, toCode);

        // Simular un tipo de cambio (esto debería obtenerse de una fuente real o API)
        ExchangeRate rate = new ExchangeRate(fromCurrency, toCurrency, LocalDate.now(), 1.2); // Ejemplo: 1.2 como tasa de cambio

        // Realizar la conversión
        Money originalMoney = new Money(amount, fromCurrency);
        Money convertedMoney = convert(originalMoney, rate);

        // Mostrar el resultado
        System.out.println("Cantidad original: " + originalMoney);
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

