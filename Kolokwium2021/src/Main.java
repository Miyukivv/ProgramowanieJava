import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException, CountryNotFoundException {
        File file=new File("Kolokwium2021/confirmed_cases.csv");
        System.out.println(file.exists());


        Country.setFiles("Kolokwium2021/confirmed_cases.csv", "Kolokwium2021/deaths.csv");
        Country country1 = Country.fromCsv("Afghanistan");
        if (country1 instanceof CountryWithoutProvinces) {
            System.out.println("kraj bez prowincji");
        } else {
            System.out.println("kraj z prowincjami");
        }
        LocalDate date = LocalDate.of(2020, 2, 24);


        CountryWithoutProvinces country2 = new CountryWithoutProvinces("Afghanistan");
        int [] afghanistanStatistics=country2.howManyConfirmedCasesAndDeaths(Country.getPath1(), Country.getPath2(), date);

        System.out.println(afghanistanStatistics[0]);
        System.out.println(afghanistanStatistics[1]);
    }
}