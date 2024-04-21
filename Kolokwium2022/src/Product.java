import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Product {
    private String name;
    private static List<Product> listOfProducts = new ArrayList<>();
    private String nameOfFile;



    public static void clearProducts() {
        listOfProducts.clear();
    }

    public static void addProducts(Function<Path, Product> fromCsv, Path path) {

        try {
            Product p = fromCsv.apply(path);
            listOfProducts.add(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product(String name, String nameOfFile) {
        this.name = name;
        this.nameOfFile = nameOfFile;
    }

    public String getName() {
        return name;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }
    public static List<Product> getListOfProducts() {
        return listOfProducts;
    }

    public static Product getProduct(String prefix) throws AmbigiousProductException {
        List<Product> foundProduct=new ArrayList<>();
        List<String> foundProductNames=new ArrayList<>();

        for (Product p : getListOfProducts()){
            if (p.getName().startsWith(prefix)){
                foundProduct.add(p);
                foundProductNames.add(p.getName());
            }
        }
        if (foundProduct.size()==0){
            throw new IndexOutOfBoundsException(String.format("Nie znaleziono produktu z prefiksem %s\n",prefix));
        }
        else if (foundProduct.size()==1){
            return foundProduct.get(0);
        }
        else{
            throw new AmbigiousProductException(foundProductNames);
        }

    }
    /*Metoda powinna zwracać cenę produktu w danym roku i miesiącu, a jeżeli dane nie mieszczą się
w zakresie 01.2010 - 03.2022, lub gdy miesiąc będzie spoza zakresu 1-12 rzucić wyjątek
IndexOutOfBoundsException. Nadpisz i zaimplementuj tę metodę w klasie NonFoodProduct.*/

    public abstract double getPrice(int year, int month);

    protected String convertToRomanDate(int year, int month){
        String [] ROMAN_MONTHS={"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
        return year+" "+ROMAN_MONTHS[month-1];
    }
}
