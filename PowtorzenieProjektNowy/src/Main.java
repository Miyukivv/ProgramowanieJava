//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

            System.out.println(DeathCauseStatistic.fromCsvLine("zgony.csv","A02.1"));

            DeathCauseStatistic p=DeathCauseStatistic.fromCsvLine("zgony.csv","A02.1");

            //Sprawdzenie czy działa metoda getBracketForAge (czy wyświetla się przedział wiekowy i liczba zgonów)
            System.out.println(p.getBracketForAge(67));


            //Sprawdzam czy działa metoda, która usuwa listę, która przechowuje  obiekty DeathCauseStatic i zapełnia ją wszystkimi danymi z pliku CSV
            DeathCauseStatisticList stats=new DeathCauseStatisticList();
            stats.repopulate("zgony.csv");

//        for (DeathCauseStatistic l : stats.getStats()){
//                System.out.println(l);
//            }

        System.out.println("\n");

        for (int i=0; i<4; i++){
            System.out.println(stats.mostDeadlyDiseases(37,4).get(i));
        }

        //Sprawdzenie opisu choroby
        ICDCodeTabular testMemory=new ICDCodeTabularOptimizedForMemory();
        System.out.println(testMemory.getDescription("A18.4"));
        System.out.println(testMemory.getDescription("A02.1"));

        //Sprawdzanie opisu choroby drugim sposobem(wywołaniem raz odczytu pliku)
        ICDCodeTabular  testForTime=new ICDCodeTabularOptimizedForTime();
        System.out.println(testForTime.getDescription("A18.4"));

        //Eksperyment
        System.out.println("\nMETODA MEMORY");
        long startTime1=System.nanoTime();
        System.out.println(testMemory.getDescription("A18.4"));
        long endTime1=System.nanoTime();
        System.out.println(endTime1-startTime1);

        //Eksperyment
        System.out.println("\nMETODA FOR TIME");
        long startTime2=System.nanoTime();
        System.out.println(testForTime.getDescription("A18.4"));
        long endTime2=System.nanoTime();
        System.out.println(endTime2-startTime2);
    }
}