import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular{
    private Map<String, String> illnessAndDescription= new HashMap<>();


    /*Klasa ICDCodeTabularOptimizedForTime powinna jednorazowo załadować wszystkie kody i opisy z pliku*/
    public ICDCodeTabularOptimizedForTime() {

        Path path=Path.of("icd10.txt");
        try (Stream<String> lines= Files.lines(path)) { //odczytujemy wszystkie linie

            lines.skip(88) //skipujemy 88 linii
                    .map(String::trim) //na każdej linii wykonywana jest metoda która usuwa białe znaki z początku i końca
                    .filter(s ->s.matches("[A-Z][0-9]{2}.*")) //od A-Z jedna litera, od 0 do 9 cyfra razy 2. *->reszta opisu (dowolna sekwencja znaków)
                    .map(s ->s.split(" ",2)) //dla każdej sekwencji wywołujemy funkcje którą definiujemy w środku, dzieli nam stringa na dwa stringi, rozdzielnik to spacja
                    .forEach(strings -> illnessAndDescription.put(strings[0],strings[1])); //dla każdego przetworzonego wrzuca chorobę, gdzie kluczem jest nazwa, a tym drugim opis
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        /*
    a jej metoda getDescription powinna zwracać wartości z wybranej struktury danych w pamięci tymczasowej.
     */

    @Override
    public String getDescription(String codeOfDisease) throws IndexOutOfBoundsException {
        return illnessAndDescription.get(codeOfDisease);
    }

}
