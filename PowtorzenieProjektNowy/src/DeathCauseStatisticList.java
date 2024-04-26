
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class DeathCauseStatisticList{
    private List<DeathCauseStatistic> stats=new ArrayList<>();

    public List<DeathCauseStatistic> getStats() {
        return stats;
    }

    /*
Napisz klasę DeathCauseStatisticList, wewnątrz której, w wybranej strukturze danych przetrzymywana będzie informacja o wszystkich obiektach DeathCauseStatistic.
Napisz metodę repopulate, przyjmującą ścieżkę do pliku, która usuwa istniejące dane z tej struktury i zapełnia ją danymi z pliku CSV.
     */

    public void repopulate(String path) {
        Function<String, String> removeTab = s -> s.replace(" ", "");
        Function<String, Integer> toZero = s -> "-".equals(s) ? 0 : Integer.parseInt(s);

        stats.clear();
        File file=new File(path);

        try (BufferedReader br=new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            line = br.readLine();
            line=br.readLine();

            while (line != null) {
                line = removeTab.apply(line);
                String partsOfLine[] = line.split(",");

                int deaths[] = new int[partsOfLine.length - 2];

                for (int i = 0; i < partsOfLine.length - 2; i++) {
                    deaths[i] = toZero.apply(partsOfLine[i + 2]);
                }
                int totalDeaths = toZero.apply(partsOfLine[1]);
                stats.add(new DeathCauseStatistic(partsOfLine[0], deaths, totalDeaths));
                line = br.readLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    W klasie DeathCauseStatisticsList napisz metodę mostDeadlyDiseases, która przyjmie wiek oraz liczbę n,
    mniejszą od liczby wymienionych chorób.

    Metoda powinna zwrócić n-elementową listę referencji na obiekty DeathCauseStatistic
    odpowiadające chorobom powodującym największą liczbę zgonów w grupie wiekowej do której przynależy podany wiek.
    Lista powinna być posortowana malejąco.
     */

    public List<DeathCauseStatistic> mostDeadlyDiseases(int age, int n){

        int index=age/5; //nie musimy nic z index, grupa wiekowa jest wyciągana już w getBracketForAge
        if (n<stats.size()){

            //Podpinamy listę do innej listy (musimy ją posortować)
            List<DeathCauseStatistic> results=new ArrayList<>(stats);

            //Sortowanie po śmierci
            results.sort((DeathCauseStatistic disease1, DeathCauseStatistic disease2)->Integer.compare(
                    disease1.getBracketForAge(age).getDeathCount(),
                    disease2.getBracketForAge(age).getDeathCount()
            ));
            Collections.reverse(results);

            return results.subList(0,n);
        }
        return null;
    }

}
