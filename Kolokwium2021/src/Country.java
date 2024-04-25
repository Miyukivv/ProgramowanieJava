import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;
public abstract class Country {
    private final String name; //Zawiera nazwę państwa

    private static String path1;
    private static String path2;

    public Country(String name) {
        this.name = name;
    }
//set - mutator
    public String getName() { //to  akcesor
        return name;
    }

    public static String getPath1() {
        return path1;
    }

    public static String getPath2() {
        return path2;
    }


    /*
W klasie Country zdefiniuj statyczne, prywatne pola zawierające ścieżkę do obu plików CSV. Napisz
statyczną metodę klasy Country o nazwie setFiles ustawiającą te dwa pliki na wartości swoich argu￾mentów.
Metoda ta powinna zweryfikować, czy pliki istnieją i można je odczytać. Jeżeli nie będzie to
możliwe, należy rzucić wyjątek FileNotFoundException podając mu jako argument konstruktora ścieżkę
do błędnego pliku.
 */
    public static void setFiles(String path1, String path2) throws FileNotFoundException {

        File file1 = new File(path1);
        File file2= new File(path2);

        if (file1.exists()){
            Country.path1=path1;
            System.out.println("File1 exist\n");
        }
        else{
            throw new FileNotFoundException(path1); //Jak rzucamy wyjątek w metodzie, to musimy i przy nazwie metody to wpisać
        }

        if (file2.exists()){
            Country.path2=path2;
            System.out.println("File2 exist\n");
        }
        else{
            throw new FileNotFoundException(path1); //Jak rzucamy wyjątek w metodzie, to musimy i przy nazwie metody to wpisać
        }
    }


    /*W klasie Country napisz publiczną, statyczną metodę fromCsv, która przyjmie jako argument napis
zawierający nazwę kraju, a zwróci polimorficzny obiekt typu Country*/


/*Polimorfizm - zdolnosc obiektu do  bycia różnymi typami obiektów
* (z prowincją i bez prowincji w tym projekcie)*/
    public static Country fromCsv(String nameOfCountry) throws IOException, CountryNotFoundException {
        /*
         Metoda fromCsv powinna otwierać
        i zamykać pliki i może założyć, że ścieżki do nich są poprawne.
         */
        File file1 = new File(getPath1());
        File file2= new File(getPath2());

        /* za pomocą tej konstrukcji od razu zamykamy plik try with resources statement*/
        try (BufferedReader br = new BufferedReader(new FileReader(file1))) {
            String lineWithCountries = br.readLine();
            CountryColumns countryColums=Country.getCountryColumns(lineWithCountries, nameOfCountry);
            String provinces=br.readLine();
            String line=br.readLine();

            if (countryColums.columnCount==1){
                CountryWithoutProvinces countryWithoutProvinces=new CountryWithoutProvinces(nameOfCountry);

                while (line!=null){
                    String[] partsOfLine = line.split(";");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
                    LocalDate actualDate = LocalDate.parse(partsOfLine[0], formatter);

                    int [] actualValueOfconfirmedCasesAndDeaths=countryWithoutProvinces.howManyConfirmedCasesAndDeaths(path1,path2,actualDate);

                    countryWithoutProvinces.addDailyStatistic(actualDate, actualValueOfconfirmedCasesAndDeaths[0],actualValueOfconfirmedCasesAndDeaths[1]);
                    line=br.readLine();
                }

                return countryWithoutProvinces;
            } else {

                CountryWithProvinces countryWithProvinces=new CountryWithProvinces(nameOfCountry, null);

                while (line!=null){
                    String[] partsOfLine = line.split(";");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
                    LocalDate actualDate = LocalDate.parse(partsOfLine[0], formatter);
                    int [] actualValueOfconfirmedCasesAndDeaths=countryWithProvinces

                    countryWithProvinces.addDailyStatistic(actualDate, actualValueOfconfirmedCasesAndDeaths[0],actualValueOfconfirmedCasesAndDeaths[1]);
                    line=br.readLine();
                }

                return new CountryWithProvinces(nameOfCountry, null);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file2))) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

/*
Wewnątrz klasy Country zdefiniuj prywatną, statyczną klasę CountryColumns.
Klasa powinna posiadać publiczne, ostateczne, całkowite pola firstColumnIndex,
columnCount ustawiane przy pomocy konstruktora
 */
    private static class CountryColumns{
        public final int firstColumnIndex;
        public final int columnCount;

        private CountryColumns(int firstColumnIndex, int columnCount) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnCount;
        }

    }
/*W klasie Country zdefiniuj prywatną, statyczną metodę getCountryColumns, która otrzyma jako parametry: napis będący pierwszym wierszem pliku CSV oraz napis zawierający poszukiwane państwo.
Metoda powinna zwrócić obiekt klasy CountryColumns zawierający informację o początkowej kolumnie oraz liczbie kolumn poświęconej państwu. Jest to jednocześnie informacja, czy państwo posiada prowincje.
Wywołaj metodę getCountryColumns wewnątrz metody fromCsv i przekaż dalej rzucany przez nią wyjątek CountryNotFoundException*/
    private static CountryColumns getCountryColumns(String row, String nameOfCountry) throws CountryNotFoundException {
        String[] partsOfCountries = row.split(";");
        List <String> countriesList = Arrays.asList(partsOfCountries);

        int indexOfCountry=countriesList.indexOf(nameOfCountry);

        if (indexOfCountry == -1){
            throw new CountryNotFoundException(nameOfCountry);
        }

        int lastIndex = countriesList.lastIndexOf(nameOfCountry);
        int numberOfColumnForCountry=lastIndex-indexOfCountry+1;
        return new CountryColumns(indexOfCountry, numberOfColumnForCountry);
    }
    private static ArrayList<LocalDate> dates=new ArrayList<>();
    private static ArrayList<Integer> confirmedCases=new ArrayList<>();
    private static ArrayList<Integer> deaths =new ArrayList<>();
    public void addDailyStatistic(LocalDate data, int confirmedCase, int death){
        dates.add(data);
        confirmedCases.add(confirmedCase);
        deaths.add(death);
    }

    /*W klasie Country napisz publiczne, czysto wirtualne metody getConfirmedCases oraz getDeaths,
które przyjmują jako parametr datę, a zwracającą odpowiednio liczbę zdiagnozowanych przypadków i
liczbę zgonów tego dnia. Zakładamy poprawność podanej daty.*/



}
