import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NonFoodProduct extends Product{
    private Double[] prices;


    private NonFoodProduct(String name, String nameOfFile, Double[] prices) {
        super(name, nameOfFile);
        this.prices = prices;
    }
/*statyczne - nie ma dostępu do pól obiektu), jest wywoływane dla klasy,
* niestatyczne dla obiektu są wywoływane*/
    public static NonFoodProduct fromCsv(Path path) {
        String name;
        Double[] prices;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            prices = Arrays.stream(scanner.nextLine().split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                    .map(value -> value.replace(",",".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                    .map(Double::valueOf) // konwertuję string na double
                    .toArray(Double[]::new); // dodaję je do nowo utworzonej tablicy
            scanner.close();

            return new NonFoodProduct(name, "nonfood/"+path.getFileName().toString(), prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getPrice(int year, int month) {
        if (year<2010  || (year>=2022 && month>3)){
            throw new IndexOutOfBoundsException(String.format("Wrong date: %d %d",year,month));
        }

        String line;
        String convertedDate=convertToRomanDate(year,month);
        Path nameOfFilePath=Path.of(getNameOfFile());
        try {
            Scanner scanner = new Scanner(nameOfFilePath);
            line=scanner.nextLine(); //odczytuje nazwe
            line = scanner.nextLine(); //odczytuje daty
            scanner.close();

            String partsOfLine[] = line.split(";");
            List<String> partsOfLineList = Arrays.asList(partsOfLine);

            int indexOfDate = partsOfLineList.indexOf(convertedDate);

            return prices[indexOfDate];
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
