import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, CountryNotFoundException {
        Country.setFiles("confirmed_cases.csv", "deaths.csv");
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