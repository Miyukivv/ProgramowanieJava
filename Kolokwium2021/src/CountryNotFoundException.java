public class CountryNotFoundException extends Exception {
    public CountryNotFoundException(String country) {
        super(String.format("Nie znaleziono państwa: %s",country));
    }
}
