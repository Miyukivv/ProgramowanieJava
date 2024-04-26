import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular{
    @Override
    /*
    Klasa ICDCodeTabularOptimizedForMemory nie powinna przetrzymywać danych w pamięci operacyjnej,
    ale za każdym wywołaniem metody getDescription powinna otwierać plik i wyszukiwać w nim opisu.
     */
    public String getDescription(String codeOfDisease) throws IndexOutOfBoundsException {

        String path="icd10.txt";
        File file=new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line=br.readLine();
            while (line!=null){
                if (line.contains(codeOfDisease)){
                    line=line.replace(codeOfDisease,"").trim();
                    return line;
                }
                    line=br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
