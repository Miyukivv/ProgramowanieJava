import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class FoodProduct extends Product{
    private String name;
    private Map<String, Double[]> provincesWithPrices;

    private FoodProduct(String name, String nameOfFile, Map<String, Double[]> provincesWithPrices) {
        super(name,nameOfFile);
        this.provincesWithPrices=provincesWithPrices;

    }



    /*Metoda ma zwracać cenę w określonym województwie przekazanym napisem składającym się z
wielkich liter (jak w plikach z danymi). Jeżeli zostanie podany napis nie pasujący do żadnego
województwa lub data będzie niewłaściwa, należy rzucić wyjątek IndexOutOfBoundsException.
Klasa powinna także nadpisywać metodę:
double getPrice(int year, int month) w taki sposób, że jako wynik będzie zwracana średnia arytmetyczna cen ze wszystkich
województw. Przetestuj działanie dwu- i trójargumentowej metody FoodProduct::getPrice w metodzie
Main::main.
*/
    public double getPrice(int year, int month, String province) {
        if (year<2010  || (year>=2022 && month>3)){
            throw new IndexOutOfBoundsException(String.format("Wrong date: %d %d\n",year,month));
        }

        if (!provincesWithPrices.containsKey(province)){
            throw new IndexOutOfBoundsException(String.format("Wrong province: %s\n",province));
        }

        String convertedDate = convertToRomanDate(year, month);
        String line;

        Path nameOfFilePath = Path.of(getNameOfFile());
        try {
            Scanner scanner = new Scanner(nameOfFilePath);
            line = scanner.nextLine(); //odczytuje nazwe
            line = scanner.nextLine(); //odczytuje daty
            scanner.close();

            String partsOfLine[] = line.split(";");
            List<String> partsOfLineList = Arrays.asList(partsOfLine);
            int indexOfDate = partsOfLineList.indexOf(convertedDate);

            //Wyciągamy wartości dla klucza (ceny dla danego województwa)
            Double[] prices = provincesWithPrices.get(province);

            //Zwracamy wartość z tablicy
            return prices[indexOfDate - 1]; //-1 bo w tej tablicy już nie mamy województw, więc indeks 0 to pierwsza cena, a nie indeks 1
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public double getPrice(int year, int month) {
        if (year<2010  || (year>=2022 && month>3)){
            throw new IndexOutOfBoundsException(String.format("Wrong date: %d %d\n",year,month));
        }
        String convertedDate = convertToRomanDate(year, month);
        String line;

        Path nameOfFilePath = Path.of(getNameOfFile());
        int indexOfDate=-1;
        try {
            Scanner scanner = new Scanner(nameOfFilePath);
            line = scanner.nextLine(); //odczytuje nazwe
            line = scanner.nextLine(); //odczytuje daty
            scanner.close();

            String partsOfLine[] = line.split(";");
            List<String> partsOfLineList = Arrays.asList(partsOfLine);
            indexOfDate = partsOfLineList.indexOf(convertedDate);

            } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double valueOfPrices=0;
        for (String key : provincesWithPrices.keySet()){
            valueOfPrices+=provincesWithPrices.get(key)[indexOfDate-1];
        }
        return valueOfPrices/provincesWithPrices.size();
    }
    public static FoodProduct fromCsv(Path path){

        String name;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli, datami
            Map<String, Double []> provincesAndPrices = new HashMap<>();
            String line;
            while (scanner.hasNextLine()){ //sprawdza czy jest kolejna linia, ale jej nie odczytuje
                line=scanner.nextLine();
                String partsOfLine[]=line.split(";");

                //ceny bez prowincji: stara tablica, od którego argumentu, rozmiar starej tablicy
                String pricesAsString[]= Arrays.copyOfRange(partsOfLine, 1, partsOfLine.length);
                Double prices[]=Arrays.stream(pricesAsString).map(value -> value.replace(",",".")).map(Double::valueOf).toArray(Double []::new);
                provincesAndPrices.put(partsOfLine[0],prices);
            }
            scanner.close();

            return new FoodProduct(name, "food/"+path.getFileName().toString(),provincesAndPrices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
