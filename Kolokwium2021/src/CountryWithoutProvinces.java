import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountryWithoutProvinces extends Country{
    public CountryWithoutProvinces(String name) {
        super(name);
    }
    /*zawartość pozwalającą zapisać ile danego dnia w tym państwie było zakażeń i zgonów.
    Klasa powinna umożliwiać zapisanie wielu takich wpisów.*/

    public int [] howManyConfirmedCasesAndDeaths(String path1, String path2, LocalDate date) throws CountryNotFoundException {

        int confirmedCasesAndDeaths[]=new int[2];
        confirmedCasesAndDeaths[0]=getValueForCountryAndDate(path1,date);
        confirmedCasesAndDeaths[1]=getValueForCountryAndDate(path2,date);
        return confirmedCasesAndDeaths;
    }

    private int getValueForCountryAndDate(String path, LocalDate date) throws CountryNotFoundException {
        File file=new File(path);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] partsOfCountries = line.split(";");
            List<String> countriesList = Arrays.asList(partsOfCountries);

            int indexOfCountry=countriesList.indexOf(getName());

            if (indexOfCountry == -1){
                throw new CountryNotFoundException(getName());
            }

            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("M/dd/yy");
            String formattedDate =date.format(pattern);

            while (line!=null){

                String[] partsOfLine = line.split(";");

                if (partsOfLine[0].equals(formattedDate)){
                    return Integer.valueOf(partsOfLine[indexOfCountry]);
                }
                line = br.readLine();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /*
    Zdefiniuj publiczną metodę addDailyStatistic, przyjmującą jako argumenty datę oraz dwie liczby
całkowite - zachorowania i zgony, która dodaje je do zaproponowanej struktury. Daty należy zapisywać
jako obiekty klasy LocalDate
     */
}
