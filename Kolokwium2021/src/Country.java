import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

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
                ArrayList<Country> provincesList=new ArrayList<>();
                List<String> countries=Arrays.asList(lineWithCountries.split(";"));

                List<String> splitedProvinces = List.of(provinces.split(";"));

                for (int i=0; i<splitedProvinces.size(); i++){
                    if (splitedProvinces.get(i).equals(nameOfCountry)){
                        provincesList.add(new CountryWithoutProvinces((splitedProvinces.get(i))));
                    }
                }

                CountryWithProvinces countryWithProvinces=new CountryWithProvinces(nameOfCountry, provincesList);

                while (line!=null){
                    String[] partsOfLine = line.split(";");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
                    LocalDate actualDate = LocalDate.parse(partsOfLine[0], formatter);
                 //   int [] actualValueOfconfirmedCasesAndDeaths=

               //     countryWithProvinces.addDailyStatistic(actualDate, actualValueOfconfirmedCasesAndDeaths[0],actualValueOfconfirmedCasesAndDeaths[1]);
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
liczbę zgonów tego dnia. Zakładamy poprawność podanej daty.
*/

    public abstract int getConfirmedCases(LocalDate date);

    public abstract int getDeaths(LocalDate date);

    /*
                results.sort((DeathCauseStatistic disease1, DeathCauseStatistic disease2)->Integer.compare(
                    disease1.getBracketForAge(age).getDeathCount(),
                    disease2.getBracketForAge(age).getDeathCount()
            ));
     */

    /*
W klasie Country napisz publiczną, statyczną metodę sortByDeaths, która przyjmie listę obiektów
Country oraz dwie daty: początkową i końcową. Metoda powinna posortować tablicę malejąco według
liczby śmierci w okresie między datą początkową, a końcową włącznie z nimi. Zakładamy poprawność
podanych dat oraz, że początkowa jest wcześniejsza niż końcowa
     */
    public static List<Country> sortByDeaths(List<Country> listOfCountry, LocalDate date1, LocalDate date2){
        return listOfCountry.stream()
                .sorted(Comparator.comparingInt(c -> getTotalDeaths(c, date1, date2)))
                .collect(Collectors.toList());
        }

    private static int getTotalDeaths(Country country, LocalDate startDate, LocalDate endDate) {
        int totalDeaths = 0;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) { //Mamy początkową datę i za każdym razem dodajemy dzień
            int deaths = country.getDeaths(date);
            if (deaths > 0) {
                totalDeaths += deaths;
            }
        }
        return totalDeaths;
    }


        /*
W klasie Country napisz publiczną metodę saveToDataFile, która przyjmie ścieżkę do pliku wynikowego. Zakładamy, że jest ona poprawna.
Metoda powinna utworzyć plik składający się z trzech kolumn
oddzielonych tabulatorami. W pierwszej kolumnie powinny znaleźć się daty w formacie d.MM.yy w drugiej liczba zdiagnozowanych przypadków w tym dniu,
a w trzeciej liczba zgonów w tym dniu. W kolejnych
wierszach pliku wynikowego należy zapisać wszystkie daty i odpowiadające im statystyki dostępne w plikach CSV.
         */
    private static Map<LocalDate, Integer> totalPerDate(String path) {
        Map<LocalDate, Integer> map = new HashMap<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path.toString()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
            String line = bf.readLine();
            line = bf.readLine();
            while ((line = bf.readLine()) != null) {
                List<String> split = Arrays.asList(line.split(";"));
                Integer sum = split.stream().skip(1).mapToInt(Integer::parseInt).sum();
                map.put(LocalDate.parse(split.get(0), formatter), sum);
            }
            bf.close();
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveToDataFile(String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString()));
            Map<LocalDate, Integer> mapOfDeaths = totalPerDate(path1);
            Map<LocalDate, Integer> mapOfInfections = totalPerDate(path2);

            for (Map.Entry<LocalDate, Integer> entry : mapOfDeaths.entrySet()) {
                bw.write(entry.getKey().format(DateTimeFormatter.ofPattern("d.M.yy")));
                bw.write("\t");
                bw.write(entry.getValue().toString());
                bw.write("\t");
                bw.write(mapOfInfections.getOrDefault(entry.getKey(), 0).toString()); // GetOrDefault handles missing keys
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/*
Metoda totalPerDate(String path):

    Ta metoda odczytuje dane z pliku podanego ścieżką path i zwraca mapę, gdzie kluczem jest data (LocalDate) a wartością jest suma liczby zgonów lub zakażeń w tej dacie.
    Otwiera plik do odczytu za pomocą BufferedReader.
    Tworzy obiekt DateTimeFormatter, który jest używany do parsowania dat z pliku tekstowego.
    Wczytuje kolejne linie pliku, dzieli je na części za pomocą separatora ; i oblicza sumę wartości liczbowych poza datą.
    Datę i sumę dodaje do mapy.
    Obsługuje wyjątek IOException poprzez rzucenie RuntimeException.

Metoda saveToDataFile(String path):

Na początku metoda tworzy obiekt BufferedWriter, który umożliwia zapis do pliku tekstowego.
Następnie dla każdej pary (klucz-wartość) w mapie zawierającej sumaryczną liczbę zgonów (mapOfDeaths) wykonuje się pętla for-each:
entry.getKey().format(DateTimeFormatter.ofPattern("d.M.yy")) formatuje klucz (którym jest data) do postaci "d.M.yy" (dzień.miesiąc.rok) i zapisuje go do pliku.
entry.getValue().toString() pobiera wartość (czyli sumaryczną liczbę zgonów) i zamienia ją na tekst, który jest zapisywany do pliku.
mapOfInfections.getOrDefault(entry.getKey(), 0).toString() pobiera wartość z mapy zawierającej sumaryczną liczbę zakażeń dla tego samego klucza (czyli daty). Jeśli klucz nie istnieje w mapie zakażeń, metoda getOrDefault zwraca wartość domyślną 0.
Następnie wartość ta jest zamieniana na tekst i zapisywana do pliku.
 Po zapisaniu wszystkich danych, zamykany jest obiekt BufferedWriter, aby zwolnić zasoby.


Map.Entry i entrySet():
Map.Entry jest interfejsem w Javie, który reprezentuje pojedynczy wpis w mapie. W przypadku tej metody, `Map.Entry
reprezentuje pojedynczą parę (klucz-wartość) w mapie, gdzie kluczem jest data (typu LocalDate), a wartością jest suma liczby zgonów lub liczby zakażeń (typu Integer).
entrySet() jest metodą interfejsu Map, która zwraca zbiór wszystkich wpisów w mapie jako obiekt typu Set<Map.Entry<K, V>>. Każdy obiekt Map.Entry zawiera
klucz i odpowiadającą mu wartość. Dzięki wywołaniu entrySet() można iterować po wszystkich wpisach w mapie za pomocą pętli for-each lub innych metod dostępnych dla zbiorów.

W przypadku metody saveToDataFile,
używając entrySet(), iterujemy po wszystkich wpisach w mapie zawierającej sumaryczną liczbę zgonów i pobieramy odpowiednie wartości z mapy zakażeń dla tych samych dat.

    */

}
