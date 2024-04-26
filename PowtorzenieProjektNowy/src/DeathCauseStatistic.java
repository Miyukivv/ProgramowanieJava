import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;
import java.util.function.Function;

public class DeathCauseStatistic {
    private String illness;
    private int numberOfDeath[];
    private int totalDeaths;


    public DeathCauseStatistic(String illness, int[] numberOfDeath, int totalDeaths) {
        this.illness = illness;
        this.numberOfDeath = numberOfDeath;
        this.totalDeaths=totalDeaths;
    }

    public String getIllness() {
        return illness;
    }

    public int[] getNumberOfDeath() {
        return numberOfDeath;
    }

    @Override
    public String toString() {
        return "DeathCauseStatistic{" +
                "illness='" + illness + '\'' +
                ", numberOfDeath=" + Arrays.toString(numberOfDeath) +
                ", totalDeaths=" + totalDeaths +
                '}';
    }

    //zwraca obiekt DeathCauseStatistic na podstawie pojedynczej linii pliku CSV
    public static DeathCauseStatistic fromCsvLine(String path, String nameOfIllness) {
        File file = new File(path);

        Function<String, String> removeTab = s -> s.replace(" ", "");
        Function<String, Integer> toZero = s -> "-".equals(s) ? 0 : Integer.parseInt(s);
        //Map<String, int[]> illnessAndDeaths = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            line = br.readLine();
            line = br.readLine();
            ArrayList<String> nameOfillnessList=new ArrayList<>();

           // int totalDeaths[]=new int[];
            while (line!=null) {
                line=removeTab.apply(line);
                String partsOfline[] = line.split(",");

                nameOfillnessList.add(partsOfline[0]);

                if (partsOfline[0].equals(nameOfIllness)){
                    {
                        int deaths[]=new int[partsOfline.length-2];

                        for (int i = 0; i < partsOfline.length - 2; i++) {

                            deaths[i]=toZero.apply(partsOfline[i + 2]);
                        }
                        int totalDeaths=toZero.apply(partsOfline[1]);
                        return  new DeathCauseStatistic(nameOfIllness,deaths,totalDeaths);
                    }
                }
                line=br.readLine();
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public class AgeBracketDeaths {
        public final int young;
        public final int old;
        public final int deathCount;

        public AgeBracketDeaths(int old, int young, int deathCount) {
            this.old = old;
            this.deathCount = deathCount;
            this.young=young;
        }

        @Override
        public String toString() {
            return "AgeBracketDeaths{" +
                    "young=" + young +
                    ", old=" + old +
                    ", deathCount=" + deathCount +
                    '}';
        }

        public int getYoung() {
            return young;
        }

        public int getOld() {
            return old;
        }

        public int getDeathCount() {
            return deathCount;
        }
    }

    public AgeBracketDeaths getBracketForAge(int age){
        //age/5  -> 16 lat /5 = 3 -> to jest numer indeksu przedziału wiekowego
        int index=age/5;

        return new AgeBracketDeaths(index*5+4,index*5, numberOfDeath[index]);
    }


}
